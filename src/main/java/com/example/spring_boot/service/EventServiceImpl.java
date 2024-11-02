package com.example.spring_boot.service;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.example.spring_boot.models.Event;
import com.example.spring_boot.repository.EventRepositoryImpl;

@Service("eventService")
public class EventServiceImpl implements EventService  {
    
    private final EventRepositoryImpl repository;

    public EventServiceImpl(EventRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    @Override
    public Collection<Event> getEventsByUserId(Long id) {
        return repository.getEventsByUserId(id);
    }

    @Override
    public Collection<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate) {

        Collection<Event> events = repository.getAllEvents();
        Collection<Event> results = new ArrayList<Event>();
        LocalDate date;

        for (Event event : events) {
            date = event.getDate();

            if (date.isAfter(startDate) || date.isEqual(startDate)) {
                if (date.isBefore(endDate) || date.isEqual(endDate)) {
                    results.add(event);
                }
            }
        }

        return results;
    }   

    @Override
    public Collection<Event> getEventsBetweenDatesByUserId(LocalDate startDate, LocalDate endDate, Long id) {

        Collection<Event> events = repository.getEventsByUserId(id);
        Collection<Event> results = new ArrayList<Event>();
        LocalDate date;

        for (Event event : events) {
            date = event.getDate();

            if (date.isAfter(startDate) || date.isEqual(startDate)) {
                if (date.isBefore(endDate) || date.isEqual(endDate)) {
                    results.add(event);
                }
            }
        }

        return results;
    }

    @Override
    public Collection<Event> getEventsForMonth(LocalDate date) {

        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

        return getEventsBetweenDates(startDate, endDate);
    }

    @Override
    public Collection<Event> getEventsForMonthByUserId(LocalDate date, Long id) {

        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

        return getEventsBetweenDatesByUserId(startDate, endDate, id);

    }

    @Override
    public Event getEvent(Long eventId) {
        return repository.getEvent(eventId);
    }

    @Override
    public Long addEvent(Long userId, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details) {
        return repository.addEvent(userId, title, date, startTime, endTime, details);
    }
    
    @Override
    public void updateEvent(Long id, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details) {
        repository.updateEvent(id, title, date, startTime, endTime, details);
    }

    @Override
    public void deleteEvent(Long id) {
        repository.deleteEvent(id);
    }

    @Override
    public int getMonthOffset(Event event) {

        int currMonth = LocalDate.now().getMonthValue();
		int currYear = LocalDate.now().getYear();
		int eventMonth = event.getDate().getMonthValue();
		int eventYear = event.getDate().getYear();

		int monthOffset = 0;

		if (eventYear > currYear) {
			monthOffset = (eventMonth - currMonth) + 12;
		}
		else if (currYear > eventYear) {
			monthOffset = (eventMonth - currMonth) - 12;
		}
		else {
			monthOffset = eventMonth - currMonth;
		}

        return monthOffset;
    }

}
