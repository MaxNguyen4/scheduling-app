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
@RequestMapping("/events")
public class EventsController {

    private final EventServiceImpl service;
	private final SecurityUtils securityUtils;

    @Autowired
    public EventsController(EventServiceImpl service, SecurityUtils securityUtils) {
        this.service = service;
		this.securityUtils = securityUtils;
    }

    @GetMapping("/{eventId}")
	@PreAuthorize("@securityUtils.getAuthenticatedUserId() == @eventService.getEvent(#eventId).userId")
	public String event(Model model, @PathVariable Long eventId, HttpSession session) {
		Event event = service.getEvent(eventId);
		int monthOffset = service.getMonthOffset(event);

		model.addAttribute("event", event);
		model.addAttribute("monthOffset", monthOffset);
		return "event";
	}

    @PostMapping("/edit")
	@PreAuthorize("@securityUtils.getAuthenticatedUserId() == @eventService.getEvent(#event.id).userId")
	public String modifyEvent(@ModelAttribute("event") Event event) {
		service.updateEvent(event.getId(), event.getTitle(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getDetails());
		int monthOffset = service.getMonthOffset(event);

		return "redirect:/calendar/" + monthOffset;
	}

	@GetMapping("/add")
	public String addEvent(@RequestParam LocalDate date, @RequestParam int monthOffset, Model model, HttpSession session) {
		Event event = new Event();
		event.setDate(date);
		event.setStartTime(LocalTime.of(0, 0));
		event.setEndTime(LocalTime.of(11, 59));

		model.addAttribute("event", event);
		model.addAttribute("monthOffset", monthOffset);

		return "add-event";
	}

    @PostMapping("/add")
	public String addEvent(@ModelAttribute("event") Event event) {
		Long userId = securityUtils.getAuthenticatedUserId();
		int monthOffset = service.getMonthOffset(event);
		
		service.addEvent(userId, event.getTitle(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getDetails());

		return "redirect:/calendar/" + monthOffset;
	}

	@GetMapping("/delete/{eventId}")
	@PreAuthorize("@securityUtils.getAuthenticatedUserId() == @eventService.getEvent(#eventId).userId")
	public String deleteEvent(@PathVariable("eventId") Long eventId, HttpSession session) {
		Event event = service.getEvent(eventId);
		int monthOffset = service.getMonthOffset(event);

		service.deleteEvent(eventId);

		return "redirect:/calendar/" + monthOffset;
	}
}
