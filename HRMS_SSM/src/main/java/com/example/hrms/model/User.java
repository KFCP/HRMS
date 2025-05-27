package com.example.hrms.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections; // For a simple, single role
// import java.util.HashSet; // If you have multiple roles from a related entity
// import java.util.Set;

public class User implements UserDetails, Serializable { // Implement UserDetails
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password; 
    
    // Fields for UserDetails interface - providing default implementations
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    // private Set<GrantedAuthority> authorities; // More complex role storage

    // Constructors
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters for existing fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // UserDetails methods implementations
    @Override
    public String getUsername() { // Already present
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() { // Already present
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, grant a default authority. This should be driven by user roles from DB in a real app.
        // If you add a roles field/collection to your User model, build the authorities from that.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        // Example for multiple roles if you had a Set<Role> roles field:
        // Set<GrantedAuthority> authorities = new HashSet<>();
        // for (Role role : this.roles) { // Assuming a Role entity with a getName() method
        //     authorities.add(new SimpleGrantedAuthority(role.getName()));
        // }
        // return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // toString (optional, good for debugging)
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", enabled=" + enabled +
               '}'; // Avoid logging password
    }
}
