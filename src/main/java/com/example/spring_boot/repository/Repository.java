package com.example.spring_boot.repository;
import com.example.spring_boot.models.Event;
import java.util.List;
import java.time.LocalDate;

public interface Repository {
    public List<Event> getAllEvents();
    public List<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate);
}
