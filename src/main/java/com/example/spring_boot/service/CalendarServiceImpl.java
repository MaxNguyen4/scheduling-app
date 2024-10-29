package com.example.spring_boot.service;

import java.util.Collection;
import java.time.LocalDate;
import java.time.LocalTime;

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
