package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.spring_boot.models.*;
import com.example.spring_boot.service.UsersServiceImpl;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {

	private final UsersServiceImpl service;

	@Autowired
	public UserController(UsersServiceImpl service) {
		this.service = service;
	}

	@GetMapping("/login")
	public String login(Model model) {

		Users user = new Users();

		model.addAttribute("user", user);
		return "user/login";
	}

	@GetMapping("/create-account")
	public String createAccount(Model model) {

		Users user = new Users();

		model.addAttribute("user", user);

		return "user/create-account";
	}

	@PostMapping("/create-account")
	public String createAccount(@ModelAttribute("user") Users user, @RequestParam("confirmPassword") String confirmPassword) {

		System.out.println(confirmPassword);

		service.createAccount(user.getUsername(), user.getPassword());

		return "user/login";
	}

	/* 
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
	*/

}