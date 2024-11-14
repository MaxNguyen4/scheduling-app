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
    public Collection<Event> getEventsForWeekByUserId(LocalDate date, Long id) {

        LocalDate startOfWeek = date;
        LocalDate endOfWeek;

        if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            startOfWeek = date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        }

        endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        return getEventsBetweenDatesByUserId(startOfWeek, endOfWeek, id);
    }

    @Override
    public Collection<Event> getEventsForDayByUserId(LocalDate date, Long id) {

        return getEventsBetweenDatesByUserId(date, date, id);

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
        LocalTime time = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        while (!time.equals(endTime)) {
            timeList.add(time);
            time = time.plusMinutes(30);
        }

        return timeList;
    }

    @Override
    public LocalTime roundToNearestHalfHour(LocalTime time) {
        int minute = time.getMinute();
        if (minute >= 45) {
            time = time.plusHours(1).withMinute(0);
        }
        else if (minute == 30 || minute == 0) {
        }
        else if (minute >= 15) {
            time = time.withMinute(30);
        }
        else {
            time = time.withMinute(0);

        }
        return time;
    }

    @Override
    public Collection<Event> roundTime(Collection<Event> events) {
        for (Event event : events) {
            event.setStartTime(roundToNearestHalfHour(event.getStartTime()));
            event.setEndTime(roundToNearestHalfHour(event.getEndTime()));

            if (event.getStartTime().equals(event.getEndTime())) {
                int minute = event.getEndTime().getMinute();

                if (minute >= 30) {
                    event.setEndTime(event.getEndTime().plusHours(1).withMinute(0));
                }
                else {
                    event.setEndTime(event.getEndTime().withMinute(30));
                }
            }
        }
        return events;
    }

    @Override
    public boolean isInTimeFrame(Event event, LocalTime timeSlot) {

        boolean result;

        LocalTime timeStart = event.getStartTime();
        LocalTime timeEnd = event.getEndTime();

        if (timeSlot.equals(timeStart) || timeSlot.equals((timeEnd.minusMinutes(30)))) {
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

    @Override
    public int getTimeSlots(Event event) {
        return 1;
    }

    // Assumes events are rounded
    @Override
    public boolean isClashing(Event event, Event event2) {
        LocalTime eventStart1 = event.getStartTime();
        LocalTime eventEnd1 = event.getEndTime();
        LocalTime eventStart2 = event2.getStartTime();
        LocalTime eventEnd2 = event2.getEndTime();
        boolean result;

        if (eventStart1.isAfter(eventStart2) && eventStart1.isBefore(eventEnd2)) {
            result = true; 
        }
        else if (eventStart2.isAfter(eventStart1) && eventStart2.isBefore(eventEnd1)) {
            result = true;
        }
        else if (eventStart1.equals(eventStart2) || eventEnd1.equals(eventEnd2)) {
            result = true;
        }
        else {
            result = false;
        }

        return result;
    }

    @Override
    public int getGridRow(Event event) {
        int row = 0;

        LocalTime startTime = event.getStartTime();
        LocalTime timeSlot = LocalTime.of(8, 0);

        while (!startTime.equals(timeSlot)) {
            timeSlot = timeSlot.plusMinutes(30);
            row += 1;
        }

        return row;
    }

    @Override
    public int getGridColumn(Event event) {
        LocalDate date = event.getDate();
        int day = date.getDayOfWeek().getValue();

        return day;
    }

    // Assumes events are rounded
    @Override
    public int getRowSpan(Event event) {
        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();

        int span = 0;

        while (!startTime.equals(endTime)) {
            startTime = startTime.plusMinutes(30);
            span += 1;
        }

        return span;
    }


}
