package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.spring_boot.models.*;
import com.example.spring_boot.service.UsersServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.example.spring_boot.util.SecurityUtils;


@Controller
@RequestMapping("/user")
public class UserController {

	private final UsersServiceImpl service;
	private final SecurityUtils securityUtils;

	@Autowired
	public UserController(UsersServiceImpl service, SecurityUtils securityUtils) {
		this.service = service;
		this.securityUtils = securityUtils;
	}

	@GetMapping("/")
	public String user(Model model) {
		return "user/profile";
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
	public String createAccount(@ModelAttribute("user") Users user, @RequestParam("confirmPassword") String confirmPassword, Model model) {

		if (confirmPassword.equals(user.getPassword())) {


			if (service.findByUsername(user.getUsername()) != null) {
				model.addAttribute("error", "Username already exists");
				return "user/create-account";
			}
			else {
				service.createAccount(user.getUsername(), user.getPassword());
			}

		}
		else {
			model.addAttribute("error", "Passwords are not the same");
			return "user/create-account";
		}


		return "user/login";
	}

	@GetMapping("/profile")
	public String profile(Model model, HttpSession session, HttpServletRequest request) {

		String referer = request.getHeader("Referer");
		if (referer != null) {
			request.getSession().setAttribute("previousPage", referer);
		}

		String username = securityUtils.getAuthenticatedUsername();
		Users user = service.findByUsername(username);

		model.addAttribute("user", user);
		return "user/profile";
	}

	@PostMapping("/edit")
	public String edit(Model model, @ModelAttribute("users") Users user, HttpServletRequest request) {


		service.changePassword(user.getUsername(), user.getPassword());

		String previousPage = (String) request.getSession().getAttribute("previousPage");
    	request.getSession().removeAttribute("previousPage");

		return previousPage != null ? "redirect:" + previousPage : "redirect:/month/0";

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