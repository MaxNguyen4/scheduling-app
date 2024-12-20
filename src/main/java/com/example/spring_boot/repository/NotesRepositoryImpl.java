package com.example.spring_boot.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.spring_boot.models.*;

@Repository
public class NotesRepositoryImpl implements NotesRepository{

    private final DataSource dataSource;


    public NotesRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    @Override
    public Notes getNote(long userId, LocalDate date) {

        Notes note = null;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM notes WHERE user_id = ? AND note_date = ?")) {

            stmt.setLong(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { 
                    long noteId = rs.getLong(1);
                    String details = rs.getString(4);

                    note = new Notes(noteId, userId, date, details);
                }

            }

        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in getNote: " + e.getMessage(), e);
        }

        return note;

    }

    @Override
    public void updateNote(Long userId, LocalDate date, String details) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE notes SET details = ? WHERE user_id = ? AND note_date = ?")) {
                stmt.setString(1, details);
                stmt.setLong(2, userId);
                stmt.setDate(3, java.sql.Date.valueOf(date));

                stmt.executeUpdate();
            
        } catch (Exception e) {
        }
    }

    @Override
    public void addNote(Long userId, LocalDate date, String details) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO notes (user_id, note_date, details) VALUES (?, ?, ?)")) {
                stmt.setLong(1, userId);
                stmt.setDate(2, java.sql.Date.valueOf(date));
                stmt.setString(3, details);

                stmt.executeUpdate();

            } catch (SQLException e) {
            }

    }
    
}
