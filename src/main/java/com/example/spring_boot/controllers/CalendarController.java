package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring_boot.models.Event;

import java.time.LocalDate;
import java.util.Collection;

import com.example.spring_boot.service.*;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarServiceImpl service;

    @Autowired
    public CalendarController(CalendarServiceImpl service) {
        this.service = service;
    }

	@GetMapping("")
    public String defaultCalendar(Model model) {
        return newMonth(model, 0);
    }

	@GetMapping("/{monthOffset}")
	public String newMonth(Model model, @PathVariable int monthOffset) {

		LocalDate selectedMonth = LocalDate.now().plusMonths(monthOffset);
		int currentDay;

		if (monthOffset == 0) {
			currentDay = LocalDate.now().getDayOfMonth();
		}
		else {
			currentDay = 32;
		}

		int daysInMonth = selectedMonth.lengthOfMonth();
		int firstDayOfMonth = selectedMonth.withDayOfMonth(1).getDayOfWeek().getValue();

		Collection<Event> events = service.getEventForMonth(selectedMonth);
		model.addAttribute("selectedMonth", selectedMonth);
		model.addAttribute("currentDay", currentDay);
		model.addAttribute("events", events);
		model.addAttribute("daysInMonth", daysInMonth);
		model.addAttribute("firstDayOfMonth", firstDayOfMonth);
		model.addAttribute("monthOffset", monthOffset);

		return "calendar";
	}

}
