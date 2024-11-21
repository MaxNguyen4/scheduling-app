package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.spring_boot.models.*;
import com.example.spring_boot.service.EventServiceImpl;
import com.example.spring_boot.util.SecurityUtils;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/week")
public class WeeklyViewController {

    private final EventServiceImpl service;
	private final SecurityUtils securityUtils;

    @Autowired
    public WeeklyViewController(EventServiceImpl service, SecurityUtils securityUtils) {
        this.service = service;
		this.securityUtils = securityUtils;
    }

    @GetMapping("")
    public String week(Model model) {
        return newWeek(model, 0);
    }

    @GetMapping("/{weekOffset}")
    public String newWeek(Model model, @PathVariable int weekOffset) {

        Long userId = securityUtils.getAuthenticatedUserId();

        LocalDate startOfWeek = service.getStartOfWeek(LocalDate.now().plusWeeks(weekOffset));

        List<LocalDate> daysOfWeek = service.getDaysOfWeek(startOfWeek);
        List<LocalTime> timeList = service.getTimeList();
        Collection<Event> events = service.getEventsForWeekByUserId(startOfWeek, userId);
        
        Collection<Event> roundedEvents = service.roundTime(events);

        List<ConflictGroup> conflictGroups = service.getConflictMapping(roundedEvents);

        for (ConflictGroup conflictGroup : conflictGroups) {
            System.out.println("GROUP -------------");

            for (SubGroup subGroups : conflictGroup.getSubGroups()) {
                System.out.println("Subgroup ---");

                for (Event event : subGroups.getEvents()) {
                    System.out.println("event");
                    System.out.println(event.getTitle());
                }
            }

        }

        
        model.addAttribute("timeSlots", timeList);
        model.addAttribute("weekDays", daysOfWeek); 

        return "views/weeklynew";
    }

    
}

/* 
hashmap:
key = event:

value = array [column number, row start, row span, sub column]
*/