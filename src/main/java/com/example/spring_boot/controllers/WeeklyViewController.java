package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.spring_boot.models.*;
import com.example.spring_boot.service.EventServiceImpl;
import com.example.spring_boot.util.SecurityUtils;

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


    @GetMapping("/")
    public String week(Model model) {

        LocalDate startOfWeek = service.getStartOfWeek(LocalDate.of(2024, 10, 13));

        List<LocalDate> daysOfWeek = service.getDaysOfWeek(startOfWeek);
        List<LocalTime> timeList = service.getTimeList();
        Collection<Event> events = service.getEventsForWeek(startOfWeek);

        System.out.println(events.size());
        
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
                timeSlot = timeSlot.plusMinutes(15);
            }
        }

        for (Map.Entry<LocalDate, Map<LocalTime, List<Event>>> dayEntry : eventMap.entrySet()) {
            LocalDate day = dayEntry.getKey();
            System.out.println("Date: " + day);
        
            Map<LocalTime, List<Event>> timeSlots = dayEntry.getValue();
            for (Map.Entry<LocalTime, List<Event>> timeEntry : timeSlots.entrySet()) {
                LocalTime time = timeEntry.getKey();
                List<Event> eventsAtTime = timeEntry.getValue();
        
                System.out.println("  Time: " + time + " -> Events: ");
                for (Event event : eventsAtTime) {
                    System.out.println("    Event Title: " + event.getTitle() + ", Start: " + event.getStartTime() + ", End: " + event.getEndTime());
                }
            }
        }
        

        model.addAttribute("weekDays", daysOfWeek);
        model.addAttribute("timeSlots", timeList);
        model.addAttribute("eventMap", eventMap);

        return "views/weekly";
    }

    
}
