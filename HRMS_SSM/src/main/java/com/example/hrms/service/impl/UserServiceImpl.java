package com.example.hrms.service.impl;

import com.example.hrms.model.User;
import com.example.hrms.mapper.UserMapper;
import com.example.hrms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // If needed for write operations

// import java.util.List; // If other methods were active

@Service // Marks this as a Spring service component
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired // Injects the UserMapper dependency
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUsername(String username) {
        // Basic implementation, directly calls the mapper
        return userMapper.findByUsername(username);
    }

    // Implement other methods from UserService if they were activated
    // For example:
    // @Override
    // @Transactional // For methods that modify data
    // public void createUser(User user) {
    //     // Potentially add hashing logic for password here before saving,
    //     // though Spring Security will typically handle this.
    //     userMapper.insertUser(user);
    // }
}
