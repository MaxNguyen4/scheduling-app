package com.example.spring_boot.models;

import java.time.LocalTime;
import java.time.LocalDate;

public class Event {

    private int id;
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String details;

    public Event(int id, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.details = details;
    }
    
    public Event(){
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }    

    public LocalTime getEndTime() {
        return this.endTime;
    }
    
    public String getDetails() {
        return this.details;
    }
}
