package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import com.example.spring_boot.models.*;
import com.example.spring_boot.service.UsersServiceImpl;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("")
public class HomePageController {


	@GetMapping("")
	public String home(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/month/0";
        } else {
            return "home";
        }
	}

}