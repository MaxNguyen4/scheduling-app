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
        int currentDay = service.currentDayInWeek(daysOfWeek);

        List<LocalTime> timeList = service.getTimeList();

        List<List<ConflictGroup>> weekConflictGroups = new ArrayList<>();

        // Initialising conflict mapping for each individual day

        for (LocalDate day : daysOfWeek) {

            Collection<Event> events = service.getEventsForDayByUserId(day, userId);
            Collection<Event> roundedEvents = service.roundTime(events);
            List<ConflictGroup> conflictGroups = service.getConflictMapping(roundedEvents);

            weekConflictGroups.add(conflictGroups);
        }
        

        HashMap<Event, int[]> eventMap = new HashMap<>();

        for (int dayNumber = 0; dayNumber < weekConflictGroups.size(); ++dayNumber) {

            System.out.print("day Number: ");
            System.out.println(dayNumber);


            List<ConflictGroup> conflictGroups = weekConflictGroups.get(dayNumber);

            for (ConflictGroup conflictGroup : conflictGroups) {

                for (int subGroupNumber = 0; subGroupNumber < conflictGroup.getSize(); ++subGroupNumber) {

                    SubGroup subGroup = conflictGroup.getSubGroup(subGroupNumber);
                    
                    for (Event event : subGroup.getEvents()) {

                        System.out.println(event.getTitle());

                        System.out.print(dayNumber + " ");
                        System.out.print(event.getRowStart() + " ");
                        System.out.print(event.getRowSpan() + " ");
                        System.out.print(subGroupNumber + " ");
                        System.out.println(conflictGroup.getSize());

                        eventMap.put(event, new int[] {
                            dayNumber,
                            event.getRowStart(),
                            event.getRowSpan(),
                            subGroupNumber,
                            conflictGroup.getSize()
                        });

                    }
                }
            }

        }

        

        
        model.addAttribute("timeSlots", timeList);
        model.addAttribute("weekDays", daysOfWeek); 
        model.addAttribute("eventMap", eventMap);
        model.addAttribute("currentDay", currentDay);

        return "views/weeklynew";
    }
    
}

/* 
hashmap:
key = event:

value = array [column number, row start, row span, sub column, total sub columns]
*/