package com.example.spring_boot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Collection;

import com.example.spring_boot.models.Event;

import com.example.spring_boot.service.ServiceImpl;


@Controller
public class WebController {

	ServiceImpl service = new ServiceImpl();

	@ModelAttribute("selectedMonth")
	public LocalDate selectedMonth() {
		return LocalDate.now().withDayOfMonth(1).plusMonths(1);
	}

	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}

	@GetMapping("/calendar")
	public String calendar(Model model, @ModelAttribute("selectedMonth") LocalDate selectedMonth) {

		Collection<Event> events = service.getEventForMonth(selectedMonth);
		model.addAttribute("events", events);

		return "calendar";
	}

}