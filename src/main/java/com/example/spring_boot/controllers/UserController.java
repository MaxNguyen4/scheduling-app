package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.spring_boot.models.*;
import com.example.spring_boot.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;


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
	public String login(@ModelAttribute("user") User userLogin, HttpSession session, Model model) {

		User user = service.login(userLogin.getUsername(), userLogin.getPassword());

		if (user != null) {
			session.setAttribute("username", user.getUsername());
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("password", user.getPassword());

			System.out.println(session.getAttribute("username"));

			return "redirect:/calendar/0";            
		}
		else {
			model.addAttribute("error", "No account found, try again");

			return "user/login";
		}
	}

	@GetMapping("/create-account")
	public String createAccount(Model model) {

		User user = new User();

		model.addAttribute("user", user);

		return "user/create-account";
	}



}