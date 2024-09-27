package com.example.spring_boot.service;

import java.util.Collection;
import java.time.LocalDate;

import com.example.spring_boot.models.Event;
import com.example.spring_boot.repository.Repository;
import com.example.spring_boot.repository.RepositoryImpl;


public class ServiceImpl implements Service  {
    
    private RepositoryImpl repository = new RepositoryImpl();

    public Collection<Event> getAllEvents() {
        return repository.getAllEvents();
    }
    public Collection<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return repository.getEventsBetweenDates(startDate, endDate);

    }


}
