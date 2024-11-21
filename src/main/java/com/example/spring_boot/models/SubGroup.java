package com.example.spring_boot.models;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import com.example.spring_boot.models.*;


public class SubGroup {

    private LocalTime minTime;
    private LocalTime maxTime;
    private List<Event> events = new ArrayList<Event>();

    public SubGroup() {
    }

    public SubGroup(LocalTime minTime, LocalTime maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public SubGroup(LocalTime minTime, LocalTime maxTime, Event event) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        events.add(event);
    }


    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    public LocalTime getMinTime() {
        return this.minTime;
    }

    public LocalTime getMaxTime() {
        return this.maxTime;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public boolean addToSubGroup(Event event) {

        boolean conflicts = true;
        boolean result = false;
    
        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();

        // Non clash cases

        // To da left To da left
        if (endTime.equals(minTime) || endTime.isBefore(minTime)) {
            minTime = startTime;
            conflicts = false;
        }

        // To da right
        if (startTime.equals(maxTime) || startTime.isAfter(maxTime)) {
            maxTime = endTime;
            conflicts = false;
        }

        if (!conflicts) {
            result = true;
            System.out.println("subgroup no conflict! yay! adding to subgroup");
            events.add(event);
        }

        System.out.println("conflict exists, moving to next subgroup");

       
        return result;
    }


}

// for event in event

// for conflict group in conflict groups

// if groups size = 0

// start conflict group