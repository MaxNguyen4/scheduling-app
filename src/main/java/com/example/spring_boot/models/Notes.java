package com.example.spring_boot.models;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class Notes {

    private long noteId;
    private long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String details;

    public Notes() {
    }

    public Notes(long userId, LocalDate date, String details) {
        this.userId = userId;
        this.date = date;
        this.details = details;
    }

    public Notes(long noteId, long userId, LocalDate date, String details) {
        this.noteId = noteId;
        this.userId = userId;
        this.date = date;
        this.details = details;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getNoteId() {
        return noteId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }
}
