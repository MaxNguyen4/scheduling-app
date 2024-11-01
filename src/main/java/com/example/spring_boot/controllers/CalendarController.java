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
public class CalendarController {

    private final CalendarServiceImpl service;
	private final SecurityUtils securityUtils;

    @Autowired
    public CalendarController(CalendarServiceImpl service, SecurityUtils securityUtils) {
        this.service = service;
		this.securityUtils = securityUtils;
    }

	@GetMapping("")
    public String defaultCalendar(Model model, HttpSession session) {
        return newMonth(model, 0, session);
    }

	@GetMapping("calendar/{monthOffset}")
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

	@GetMapping("/events/{eventId}")
	@PreAuthorize("@securityUtils.getAuthenticatedUserId() == @calendarService.getEvent(#eventId).userId")
	public String event(Model model, @PathVariable Long eventId, HttpSession session) {
		Event event = service.getEvent(eventId);
		int monthOffset = service.getMonthOffset(event);

		model.addAttribute("event", event);
		model.addAttribute("monthOffset", monthOffset);
		return "event";
	}

	@PostMapping("/events")
	@PreAuthorize("@securityUtils.getAuthenticatedUserId() == @calendarService.getEvent(#event.id).userId")
	public String modifyEvent(@ModelAttribute("event") Event event) {
		service.updateEvent(event.getId(), event.getTitle(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getDetails());
		int monthOffset = service.getMonthOffset(event);

		return "redirect:/calendar/" + monthOffset;
	}

	@GetMapping("/events/add")
	public String addEvent(@RequestParam LocalDate date, @RequestParam int monthOffset, Model model, HttpSession session) {
		Event event = new Event();
		event.setDate(date);
		event.setStartTime(LocalTime.of(0, 0));
		event.setEndTime(LocalTime.of(11, 59));

		model.addAttribute("event", event);
		model.addAttribute("monthOffset", monthOffset);

		return "add-event";
	}

	@PostMapping("/events/add")
	public String addEvent(@ModelAttribute("event") Event event) {
		Long userId = securityUtils.getAuthenticatedUserId();
		int monthOffset = service.getMonthOffset(event);
		
		service.addEvent(userId, event.getTitle(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getDetails());

		return "redirect:/calendar/" + monthOffset;
	}

	@GetMapping("/events/delete/{eventId}")
	@PreAuthorize("@securityUtils.getAuthenticatedUserId() == @calendarService.getEvent(#eventId).userId")
	public String deleteEvent(@PathVariable("eventId") Long eventId, HttpSession session) {
		Event event = service.getEvent(eventId);
		int monthOffset = service.getMonthOffset(event);

		service.deleteEvent(eventId);

		return "redirect:/calendar/" + monthOffset;
	}

}
