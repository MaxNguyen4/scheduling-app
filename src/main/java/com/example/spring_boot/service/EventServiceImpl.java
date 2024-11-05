package com.example.spring_boot.service;

import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

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
    public Collection<Event> getEventsForWeek(LocalDate date) {

        LocalDate startOfWeek = date;
        LocalDate endOfWeek;

        if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            startOfWeek = date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        }

        endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        System.out.println(startOfWeek);
        System.out.println(endOfWeek);

        return getEventsBetweenDates(startOfWeek, endOfWeek);
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

    @Override
    public LocalDate getStartOfWeek(LocalDate date) {

        LocalDate startOfWeek = date;

        if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            startOfWeek = date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        }

        return startOfWeek;
    }

    @Override
    public List<LocalDate> getDaysOfWeek(LocalDate date) {
        List<LocalDate> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDays.add(date.plusDays(i));
        }

        return weekDays;
    }

    @Override
    public List<LocalTime> getTimeList() {
        List<LocalTime> timeList = new ArrayList<>();
        LocalTime time = LocalTime.of(6, 0);
        LocalTime endTime = LocalTime.MIDNIGHT;

        while (!time.equals(endTime)) {
            timeList.add(time);
            time = time.plusMinutes(30);
        }

        return timeList;
    }

    @Override
    public LocalTime roundToNearestQuarterHour(LocalTime time) {
        int minute = time.getMinute();
        int roundedMinutes = (minute + 7) / 15 * 15;
        return time.withMinute(roundedMinutes).withSecond(0).withNano(0);
    }

    @Override
    public Collection<Event> roundTime(Collection<Event> events) {
        for (Event event : events) {
            event.setStartTime(roundToNearestQuarterHour(event.getStartTime()));
            event.setEndTime(roundToNearestQuarterHour(event.getEndTime()));
        }
        return events;
    }

    @Override
    public boolean isInTimeFrame(Event event, LocalTime timeSlot) {

        boolean result;

        LocalTime timeStart = event.getStartTime();
        LocalTime timeEnd = event.getEndTime();

        if (timeSlot.equals(timeStart) || timeSlot.equals((timeEnd.minusMinutes(15)))) {
            result = true;
        }
        else if (timeSlot.isAfter(timeStart) && timeSlot.isBefore(timeEnd)) {
            result = true;
        }
        else {
            result = false;
        }

        return result;
    }

    


}
