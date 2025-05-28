package com.example.hrms.config;

import com.example.hrms.service.UserService; // Your existing UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity // Enables Spring Security's web security support and integrates it with Spring MVC.
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Uses BCrypt hashing algorithm for passwords.
        // Ensure the 'password' column in your 'users' table is wide enough (e.g., VARCHAR(72)).
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // This bean provides user-specific data to Spring Security.
        // The lambda expression delegates to UserService.findByUsername().
        // Your com.example.hrms.model.User class has been updated to implement UserDetails.
        return username -> userService.findByUsername(username);
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // This authentication provider uses the UserDetailsService and PasswordEncoder
        // to authenticate users.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Permit access to static resources (CSS, JavaScript, images) for all users.
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // Permit access to login, logout, and the root home page for all users.
                .requestMatchers("/login", "/logout", "/").permitAll() 
                
                // TODO: Configure role-based access for specific paths.
                // Example: Only users with 'ADMIN' role can access '/users/**'.
                // This requires 'User.getAuthorities()' to provide 'ROLE_ADMIN'.
                // .requestMatchers("/users/**").hasRole("ADMIN") 
                // For now, any authenticated user can access /users, /employees, /positions.
                // .requestMatchers("/users/**").authenticated() // Example if not using specific roles yet for /users

                // All other requests must be authenticated (user must be logged in).
                .anyRequest().authenticated() 
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Specifies the custom login page URL.
                .loginProcessingUrl("/login") // Spring Security will process POST requests to this URL.
                .defaultSuccessUrl("/index", true) // Redirect to /index upon successful login.
                .failureUrl("/login?error=true") // Redirect to /login?error=true on login failure.
                .permitAll() // Allow access to the loginPage and loginProcessingUrl for all.
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL that triggers logout.
                .logoutSuccessUrl("/login?logout=true") // Redirect to /login?logout=true after successful logout.
                .invalidateHttpSession(true) // Invalidate the HTTP session.
                .deleteCookies("JSESSIONID") // Delete the JSESSIONID cookie.
                .permitAll() // Allow access to the logoutUrl for all.
            );
            
            // CSRF (Cross-Site Request Forgery) Protection:
            // CSRF protection is enabled by default in modern Spring Security.
            // This means POST requests (like login, add/edit forms) will require a CSRF token.
            // Spring's <form:form> tag automatically includes this token if you use it.
            // If using plain HTML forms for POST, you'd need to manually include the token:
            // <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            // To temporarily disable CSRF for testing (NOT recommended for production):
            // .csrf(csrf -> csrf.disable()); 

        return http.build();
    }
}
