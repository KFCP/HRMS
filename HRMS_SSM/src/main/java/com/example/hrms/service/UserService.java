package com.example.hrms.service;

import com.example.hrms.model.User;
import java.util.List;

public interface UserService {
    User findByUsername(String username);
    // User authenticate(String username, String password); // This might change with Spring Security
    // List<User> getAllUsers();
    // void createUser(User user);
    // User findUserById(Long id);
}
