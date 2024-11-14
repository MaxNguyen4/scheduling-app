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

        Long userId = securityUtils.getAuthenticatedUserId();

        LocalDate startOfWeek = service.getStartOfWeek(LocalDate.now().plusWeeks(weekOffset));

        List<LocalDate> daysOfWeek = service.getDaysOfWeek(startOfWeek);
        List<LocalTime> timeList = service.getTimeList();
        Collection<Event> events = service.getEventsForWeekByUserId(startOfWeek, userId);
        
        Collection<Event> roundedEvents = service.roundTime(events);

        HashMap<Event, int[]> eventMap = new HashMap<>();

        for (Event event : roundedEvents) {
            int conflicts = 0;
            int conflictPosition = 1;

            for (Event otherEvent : roundedEvents) {
                if (event != otherEvent) {
                    if (event.getDate().equals(otherEvent.getDate())) {

                        if (service.isClashing(otherEvent, event)) {
                            conflicts += 1;

                            if (otherEvent.getStartTime().isBefore(event.getStartTime())) {
                                conflictPosition += 1;
                            }
                        }
                    }
                }
            }

            eventMap.put(event, new int[] {
                service.getGridColumn(event),
                service.getGridRow(event),
                service.getRowSpan(event),
                conflicts,
                conflictPosition
            });
        }

        for (Map.Entry<Event, int[]> entry : eventMap.entrySet()) {
            Event event = entry.getKey();
            int[] values = entry.getValue();
        
            System.out.println("Event: " + event.getTitle()); // Customize this to print relevant details of the Event
            System.out.println("Grid Column: " + values[0]);
            System.out.println("Grid Row: " + values[1]);
            System.out.println("Row Span: " + values[2]);
            System.out.println("Conflicts: " + values[3]);
            System.out.println("Conflict Position: " + values[4]);
            System.out.println("----------------------------------");
        }
        

        

        model.addAttribute("timeSlots", timeList);
        model.addAttribute("weekDays", daysOfWeek);

        return "views/weeklynew";
    }

    
}

/* 
hashmap:
key = event:

value = array [column number, row start, row span, number of conflicts, which number of conflict]
*/