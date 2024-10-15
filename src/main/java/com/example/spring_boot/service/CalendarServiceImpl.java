package com.example.spring_boot.service;

import java.util.Collection;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.spring_boot.models.Event;
import com.example.spring_boot.repository.CalendarRepositoryImpl;

@Service
public class CalendarServiceImpl implements CalendarService  {
    
    private final CalendarRepositoryImpl repository;

    public CalendarServiceImpl(CalendarRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    @Override
    public Collection<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return repository.getEventsBetweenDates(startDate, endDate);

    }   

    @Override
    public Collection<Event> getEventForMonth(LocalDate date) {

        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

        return repository.getEventsBetweenDates(startDate, endDate);
    }


}
