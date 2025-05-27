package com.example.hrms.config;

import com.example.hrms.service.UserService; // Your existing UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// For Spring Security 5.7+ component-based setup, WebSecurityConfigurerAdapter is deprecated.
// We define SecurityFilterChain as a bean instead.
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; 
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Spring Security will use this to load user details.
        // It expects a UserDetailsService implementation.
        // Your UserService needs to implement UserDetailsService or you need an adapter.
        // For now, assuming UserService can be adapted or directly used if it
        // has a method like loadUserByUsername that returns Spring Security UserDetails.
        // This will be refined in the next step (Update User Service for Security).
        // Placeholder:
        return username -> userService.findByUsername(username); 
        // This lambda implicitly requires your User model to implement UserDetails
        // or for UserService.findByUsername to return a UserDetails object.
        // This will be the focus of the next step.
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow static resources
                .requestMatchers("/login", "/logout").permitAll() // Allow access to login and logout pages
                // .requestMatchers("/users/**").hasRole("ADMIN") // Example: only ADMIN can access /users (requires roles setup)
                                                                // For now, this might cause issues as roles aren't defined.
                                                                // Let's comment it out for initial setup and use authenticated()
                // .requestMatchers("/users/**").authenticated() 
                .requestMatchers("/").permitAll() // Allow home page for all
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Custom login page
                .loginProcessingUrl("/login") // URL to submit the username and password to
                .defaultSuccessUrl("/index", true) // Redirect to /index upon successful login
                .failureUrl("/login?error=true") // URL to redirect to on login failure
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL to trigger logout
                .logoutSuccessUrl("/login?logout=true") // Redirect to login page with logout message
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID") // Delete session cookie
                .permitAll()
            );
            // .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity in testing, enable in production with proper token handling

        return http.build();
    }
}
