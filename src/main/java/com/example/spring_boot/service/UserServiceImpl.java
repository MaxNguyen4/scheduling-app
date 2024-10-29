package com.example.spring_boot.service;
import org.springframework.stereotype.Service;
import com.example.spring_boot.models.*;
import com.example.spring_boot.repository.*;

@Service
public class UserServiceImpl {

    private final UserRepositoryImpl repository;

    public UserServiceImpl(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    public User login(String username, String password) {

        User user = null;
        
        User userSearch = repository.findByUsername(username);

        if (userSearch != null) {
            if (userSearch.getPassword().equals(password)) {
                user = userSearch;
            }
        }

        return user;
    }

    
}
