package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

        LocalDate startOfWeek = service.getStartOfWeek(LocalDate.of(2024, 10, 13).plusWeeks(weekOffset));

        List<LocalDate> daysOfWeek = service.getDaysOfWeek(startOfWeek);
        List<LocalTime> timeList = service.getTimeList();
        Collection<Event> events = service.getEventsForWeek(startOfWeek);
        
        events = service.roundTime(events);

        Map<LocalDate, Map<LocalTime, List<Event>>> eventMap = new HashMap<>();
        for (LocalDate day : daysOfWeek) {
            eventMap.put(day, new HashMap<>());

            for (LocalTime time : timeList) {
                eventMap.get(day).put(time, new ArrayList<Event>());
            }
        }

        for (Event event : events) {
            LocalDate eventDate = event.getDate();
            LocalTime startTime = event.getStartTime();
            LocalTime endTime = event.getEndTime();
            LocalTime timeSlot = startTime;
    
            while (!timeSlot.isAfter(endTime)) {
                if (eventMap.containsKey(eventDate)) {
                    if (eventMap.get(eventDate).containsKey(timeSlot)) {
                        if (service.isInTimeFrame(event, timeSlot)) {
                            eventMap.get(eventDate).get(timeSlot).add(event);
                        }
                    }
                }
                timeSlot = timeSlot.plusMinutes(30);
            }
        }

        model.addAttribute("weekDays", daysOfWeek);
        model.addAttribute("timeSlots", timeList);
        model.addAttribute("eventMap", eventMap);

        return "views/weekly";
    }

    
}
