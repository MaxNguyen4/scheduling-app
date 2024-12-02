package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
@RequestMapping("/day")
public class DailyViewController {

    private final EventServiceImpl service;
    private final SecurityUtils securityUtils;

    @Autowired
    public DailyViewController(EventServiceImpl service, SecurityUtils securityUtils) {
        this.service = service;
        this.securityUtils = securityUtils;
    }

    @GetMapping("")
    public String home(Model model) {

        Long id = securityUtils.getAuthenticatedUserId();

        LocalDate date = LocalDate.of(2024,11,12);
        Collection<Event> events = service.getEventsForDayByUserId(date, id);
        List<LocalTime> timeList = service.getTimeList();
        Collection<Event> roundedEvents = service.roundTime(events);

        List<List<Event>> eventColumns = new ArrayList<>();

        model.addAttribute("eventColumns", eventColumns);
        model.addAttribute("timeList", timeList);

        return "views/daily";
    }
    
}
