package com.example.spring_boot.repository;

import com.example.spring_boot.models.*;

public interface UserRepository {
    
    public User findByUsername(String username);
}
