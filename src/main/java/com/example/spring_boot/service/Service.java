package com.example.spring_boot.service;

import java.util.Collection;
import java.time.LocalDate;

import com.example.spring_boot.models.Event;

public interface Service {
    public Collection<Event> getAllEvents();
    public Collection<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate);
}
