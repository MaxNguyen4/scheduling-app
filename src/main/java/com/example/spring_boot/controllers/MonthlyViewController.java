package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.spring_boot.models.Event;
import com.example.spring_boot.util.SecurityUtils;
import java.time.*;
import java.util.Collection;

import com.example.spring_boot.service.*;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class MonthlyViewController {

    private final EventServiceImpl service;
	private final SecurityUtils securityUtils;

    @Autowired
    public MonthlyViewController(EventServiceImpl service, SecurityUtils securityUtils) {
        this.service = service;
		this.securityUtils = securityUtils;
    }

	@GetMapping("")
    public String defaultCalendar(Model model, HttpSession session) {
        return newMonth(model, 0, session);
    }

	@GetMapping("month/{monthOffset}")
	public String newMonth(Model model, @PathVariable int monthOffset, HttpSession session) {
	
		Long userId = securityUtils.getAuthenticatedUserId();
		LocalDate localDate = LocalDate.now().plusMonths(monthOffset);

		int currentDay = (monthOffset == 0) ? LocalDate.now().getDayOfMonth() : 32;

		int daysInMonth = localDate.lengthOfMonth();
		int firstDayOfMonth = localDate.withDayOfMonth(1).getDayOfWeek().getValue();

		Collection<Event> events = service.getEventsForMonthByUserId(localDate, userId);
		model.addAttribute("localDate", localDate);
		model.addAttribute("currentDay", currentDay);
		model.addAttribute("events", events);
		model.addAttribute("daysInMonth", daysInMonth);
		model.addAttribute("firstDayOfMonth", firstDayOfMonth);
		model.addAttribute("monthOffset", monthOffset);

		return "calendar";
	}



}
