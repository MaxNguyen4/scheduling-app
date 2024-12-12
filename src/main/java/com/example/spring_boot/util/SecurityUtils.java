package com.example.spring_boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.example.spring_boot.models.Users;
import com.example.spring_boot.repository.UserRepositoryImpl;

@Component
public class SecurityUtils {

    @Autowired
    private UserRepositoryImpl repository;

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public Long getAuthenticatedUserId() {

        String username = getAuthenticatedUsername();
        Long userId = repository.findByUsername(username).getUserId();

        return userId;
    }
}
    

