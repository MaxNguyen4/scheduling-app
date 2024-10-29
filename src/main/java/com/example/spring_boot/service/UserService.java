package com.example.spring_boot.service;

import com.example.spring_boot.models.*;

public interface UserService {
    public User login(String username, String password);
}
