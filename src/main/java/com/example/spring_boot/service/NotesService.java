package com.example.spring_boot.service;

import com.example.spring_boot.models.*;
import java.time.LocalDate;

public interface NotesService {

    public Notes getNote(long userId, LocalDate date);
    public void updateNote(long userId, LocalDate date, String details);
    public void addNote(long userId, LocalDate date, String details);
    
}
