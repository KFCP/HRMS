package com.example.hrms.mapper;

import com.example.hrms.model.User;
import org.apache.ibatis.annotations.Param; // Important for multiple parameters
import java.util.List;

public interface UserMapper {
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    User findByUsername(@Param("username") String username);
    
    // Potentially other methods like:
    // void insertUser(User user);
    // User findUserById(Long id);
    // List<User> findAllUsers();
}
