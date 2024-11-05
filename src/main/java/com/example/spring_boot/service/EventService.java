package com.example.spring_boot.service;

import java.util.Collection;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import com.example.spring_boot.models.Event;

public interface EventService {
    public Collection<Event> getAllEvents();
    public Collection<Event> getEventsByUserId(Long id);
    public Collection<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate);
    public Collection<Event> getEventsForMonth(LocalDate date);
    public Collection<Event> getEventsForWeek(LocalDate date);
    public Collection<Event> getEventsBetweenDatesByUserId(LocalDate startDate, LocalDate endDate, Long id);
    public Collection<Event> getEventsForMonthByUserId(LocalDate date, Long id);
    public Event getEvent(Long eventId);
    public Long addEvent(Long userId, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details);
    public void updateEvent(Long id, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details);
    public void deleteEvent(Long id);


    public int getMonthOffset(Event event);
    public LocalDate getStartOfWeek(LocalDate date);
    public List<LocalDate> getDaysOfWeek(LocalDate date);
    public List<LocalTime> getTimeList();
    public LocalTime roundToNearestHalfHour(LocalTime time);
    public Collection<Event> roundTime(Collection<Event> events);
    public boolean isInTimeFrame(Event event, LocalTime timeSlot);
    public int getTimeSlots(Event event);

}
