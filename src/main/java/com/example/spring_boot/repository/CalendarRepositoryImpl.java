package com.example.spring_boot.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.spring_boot.models.Event;

import org.springframework.stereotype.Repository;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

@Repository
public class CalendarRepositoryImpl implements CalendarRepository {

    private final DataSource dataSource;

    public CalendarRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<Event>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM events");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong(1);
                Long userId = rs.getLong(2);
                String title = rs.getString(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                LocalTime startTime = rs.getTime(5).toLocalTime();
                LocalTime endTime = rs.getTime(6).toLocalTime();
                String details = rs.getString(7);

                Event event = new Event(id, userId, title, date, startTime, endTime, details);
                events.add(event);
            }
            
            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in getAllEvents" + e.getMessage(), e);
        }

        return events;
    }

    @Override
    public List<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Event> events = new ArrayList<Event>();

        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM events WHERE event_date BETWEEN ? AND ?");
            
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);

            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong(1);
                Long userId = rs.getLong(2);
                String title = rs.getString(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                LocalTime startTime = rs.getTime(5).toLocalTime();
                LocalTime endTime = rs.getTime(6).toLocalTime();
                String details = rs.getString(7);

                Event event = new Event(id, userId, title, date, startTime, endTime, details);
                events.add(event);
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in getEventsBetweenDates" + e.getMessage(), e);
        }

        return events;
    }

    @Override
    public Event getEvent(Long eventId) {

        Event event = null;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM events WHERE event_id = ?")) {

            stmt.setLong(1, eventId);

            System.out.println(stmt);  // For debugging

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {  // Only process if a result exists
                    Long id = rs.getLong(1);
                    Long userId = rs.getLong(2);
                    String title = rs.getString(3);
                    LocalDate date = rs.getDate(4).toLocalDate();
                    LocalTime startTime = rs.getTime(5).toLocalTime();
                    LocalTime endTime = rs.getTime(6).toLocalTime();
                    String details = rs.getString(7);

                    event = new Event(id, userId, title, date, startTime, endTime, details);
                }
            }

        } catch (SQLException e) {
            throw new UncategorizedScriptException("Error in getEvent: " + e.getMessage(), e);
        }

        return event;
    }


}
    
