package com.example.spring_boot.service;
import org.springframework.stereotype.Service;
import com.example.spring_boot.models.Users;
import com.example.spring_boot.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Service
public class UsersServiceImpl {

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    private final UserRepositoryImpl repository;

    public UsersServiceImpl(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    public void createAccount(String username, String password) {

        username = username.toLowerCase();

        UserBuilder userBuilder = User.withUsername(username)
                                    .password(password)
                                    .roles("USER");

        userDetailsManager.createUser(userBuilder.build());
    }

    public Users findByUsername(String username) {

        Users user = repository.findByUsername(username);

        return user;

    }


    
}
