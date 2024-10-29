package com.example.spring_boot.service;

import java.util.Collection;
import java.time.LocalDate;
import java.time.LocalTime;
import com.example.spring_boot.models.Event;

public interface CalendarService {
    public Collection<Event> getAllEvents();
    public Collection<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate);
    public Collection<Event> getEventForMonth(LocalDate date);
    public Event getEvent(Long eventId);
    public Long addEvent(Long userId, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details);
    public void updateEvent(Long id, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details);
    public void deleteEvent(Long id);


    public int getMonthOffset(Event event);

}
