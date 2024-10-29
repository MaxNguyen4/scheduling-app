package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.spring_boot.models.*;
import com.example.spring_boot.service.UserServiceImpl;


@Controller
@RequestMapping("/user")
public class UserController {

	private final UserServiceImpl service;

	@Autowired
	public UserController(UserServiceImpl service) {
		this.service = service;
	}

	@GetMapping("")
	public String home(Model model) {
		return "home";
	}

	@GetMapping("/login")
	public String login(Model model) {

		User user = new User();

		model.addAttribute("user", user);
		return "user/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") User user) {

		System.out.println(user.getUsername());

		User userReq = service.login(user.getUsername(), user.getPassword());

		if (userReq == null) {
			System.out.println("NOT FOUND");
		}
		else {
			System.out.println("FOUND");
		}

		return "user/login";
	}



}