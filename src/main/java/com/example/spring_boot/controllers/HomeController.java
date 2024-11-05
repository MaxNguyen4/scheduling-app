package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.spring_boot.models.*;
import com.example.spring_boot.service.EventServiceImpl;
import com.example.spring_boot.util.SecurityUtils;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String home(Model model) {

        return "views/dashboard";
    }
    
}
