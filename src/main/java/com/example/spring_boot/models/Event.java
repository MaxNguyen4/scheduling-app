package com.example.spring_boot.models;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Event {

    private Long id;
    private Long userId;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String details;

    public Event(Long id, Long userId, String title, LocalDate date, LocalTime startTime, LocalTime endTime, String details) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.details = details;
    }
    
    public Event(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
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

    public int getRowSpan() {
        int span = 0;

      
        

        LocalTime tempTime = startTime;

        // before 8 
        if (startTime.isBefore(LocalTime.of(8,0))) {
            span = 2;
            tempTime = LocalTime.of(8, 0);
        }

        // After 11
        if (endTime.isAfter(LocalTime.of(23,0))) {
            span = 2;

            if (startTime.isBefore(LocalTime.of(8,0))) {
                span = 4;
            }

            endTime = LocalTime.of(23, 0);
        }

        while (!tempTime.equals(endTime)) {
            tempTime = tempTime.plusMinutes(30);
            span += 1;
        }

        return span;
    }

    public int getRowStart() {

        int row = 0;

        LocalTime timeSlot = LocalTime.of(7, 0);

        // Before 8
        if (startTime.isBefore(LocalTime.of(8,0))) {
            return 0;
        }

        // After 11
        if (startTime.isAfter(LocalTime.of(23,0))) {
            return 35;
        }

        while (!startTime.equals(timeSlot)) {
            timeSlot = timeSlot.plusMinutes(30);
            row += 1;
        }

        return row;

    }
}
