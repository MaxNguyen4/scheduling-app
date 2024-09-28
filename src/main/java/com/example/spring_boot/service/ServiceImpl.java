package com.example.spring_boot.service;

import java.util.Collection;
import java.time.LocalDate;

import com.example.spring_boot.models.Event;
import com.example.spring_boot.repository.RepositoryImpl;


public class ServiceImpl implements Service  {
    
    private RepositoryImpl repository = new RepositoryImpl();

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
