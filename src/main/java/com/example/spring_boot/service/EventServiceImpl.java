package com.example.spring_boot.service;

import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

import com.example.spring_boot.models.ConflictGroup;
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
        LocalTime endTime = LocalTime.of(23, 0);

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
    public List<ConflictGroup> getConflictMapping(Collection<Event> events) {

        List<Event> eventsList = (events instanceof List)
            ? (List<Event>) events
            : new ArrayList<>(events);

        eventsList.sort(Comparator.comparing(Event::getStartTime));

        List<ConflictGroup> conflictGroups = new ArrayList<ConflictGroup>();

        if (!events.isEmpty()) {
            ConflictGroup initGroup = new ConflictGroup(eventsList.get(0));
            conflictGroups.add(initGroup);
        }
        else {
            return conflictGroups;
        }

        for (int i = 1; i < eventsList.size(); ++i) {
            
            Event event = eventsList.get(i);

            boolean placed = false;

            for (ConflictGroup conflictGroup : conflictGroups) {
                placed = conflictGroup.addToConflictGroup(event);

                if (placed) {
                    break;
                }
            }

            if (!placed) {
                ConflictGroup newGroup = new ConflictGroup(event);
                conflictGroups.add(newGroup);
            }

        }
    
        return conflictGroups; 
    }

    @Override
    public int currentDayInWeek(List<LocalDate> week) {

        int currDay = 0;

        for (int i = 0; i < week.size(); ++i) {
            if (week.get(i).equals(LocalDate.now())) {
                currDay = i;
            }
        }

        return currDay;
    }

    @Override
    public List<Event> sortEvents(Collection<Event> events) {

        List<Event> eventsList = (events instanceof List)
            ? (List<Event>) events
            : new ArrayList<>(events);

        eventsList.sort(Comparator.comparing(Event::getStartTime));

        return eventsList;
    }



}
