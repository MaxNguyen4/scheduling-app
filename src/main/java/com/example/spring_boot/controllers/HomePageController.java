package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.spring_boot.models.*;
import com.example.spring_boot.service.UsersServiceImpl;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("")
public class HomePageController {


	@GetMapping("")
	public String home(Model model) {
		return "home";
	}

}