package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		return "home";
	}

}