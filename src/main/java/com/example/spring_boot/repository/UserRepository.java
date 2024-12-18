package com.example.spring_boot.repository;

import com.example.spring_boot.models.*;

public interface UserRepository {
    
    public Users findByUsername(String username);

    public void changePassword(String username, String password);
}
