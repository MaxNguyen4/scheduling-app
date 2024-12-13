package com.example.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.spring_boot.models.*;
import com.example.spring_boot.service.EventServiceImpl;
import com.example.spring_boot.service.NotesServiceImpl;
import com.example.spring_boot.util.SecurityUtils;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/day")
public class DailyViewController {

    private final EventServiceImpl eventService;
    private final SecurityUtils securityUtils;
    private final NotesServiceImpl notesService;

    @Autowired
    public DailyViewController(EventServiceImpl eventService, SecurityUtils securityUtils, NotesServiceImpl notesService) {
        this.eventService = eventService;
        this.securityUtils = securityUtils;
        this.notesService = notesService;
    }

    @GetMapping("")
    public String day(Model model, HttpSession session) {
        return newDay(model, 0, session);
    }

    @GetMapping("/{dayOffset}")
    public String newDay(Model model, @PathVariable int dayOffset, HttpSession session) {

        Long userId = securityUtils.getAuthenticatedUserId();
        LocalDate date = LocalDate.now().plusDays(dayOffset);
        Collection<Event> events = eventService.getEventsForDayByUserId(date, userId);
        List<Event> sortedEvents = eventService.sortEvents(events);
        Notes note = notesService.getNote(userId, date);

        if (note == null) {
            note = new Notes(userId, date, "");
        }

        model.addAttribute("note", note);
        model.addAttribute("events", sortedEvents);
        model.addAttribute("date", date);

        return "views/daily";
    }    

    @PostMapping("/editnote")
    public String editNote(@ModelAttribute("note") Notes note) {

        if (note.getDetails() != "") {
            if (notesService.getNote(note.getUserId(), note.getDate()) == null) {

                notesService.addNote(note.getUserId(), note.getDate(), note.getDetails());
            }
            else {

                notesService.updateNote(note.getUserId(), note.getDate(), note.getDetails());
            }
        }
        return "redirect:/day/0";
    }
}
