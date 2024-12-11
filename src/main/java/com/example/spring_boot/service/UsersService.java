package com.example.spring_boot.service;

import com.example.spring_boot.models.*;

public interface UsersService {
    public Users login(String username, String password);
    public void createAccount(String username, String password);
    public boolean findByUsername(String username);
}
