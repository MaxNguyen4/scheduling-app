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

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

public class RepositoryImpl implements Repository {

    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:file:./database/calendar_db");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("password");

        return dataSourceBuilder.build();
    }
    

    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<Event>();

        try {
            Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM events");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                LocalDate date = rs.getDate(3).toLocalDate();
                LocalTime startTime = rs.getTime(4).toLocalTime();
                LocalTime endTime = rs.getTime(5).toLocalTime();
                String details = rs.getString(6);

                Event event = new Event(id, title, date, startTime, endTime, details);
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


    public List<Event> getEventsBetweenDates(LocalDate startDate, LocalDate endDate) {

        List<Event> events = new ArrayList<Event>();

        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);

        try {
            Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM events WHERE event_date BETWEEN ? AND ?");
            
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                LocalDate date = rs.getDate(3).toLocalDate();
                LocalTime startTime = rs.getTime(4).toLocalTime();
                LocalTime endTime = rs.getTime(5).toLocalTime();
                String details = rs.getString(6);

                Event event = new Event(id, title, date, startTime, endTime, details);
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

}
    
