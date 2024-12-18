package com.example.spring_boot.repository;

import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.example.spring_boot.models.*;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final DataSource dataSource;


    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Users findByUsername(String username) {

        Users user = null;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
                stmt.setString(1, username);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    user = new Users(rs.getLong("user_id"), rs.getString("username"), rs.getString("password"));
                }
                
        } catch (Exception e) {
        }

        return user;

    }

    @Override
    public void changePassword(String username, String password) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {
                stmt.setString(1, password);
                stmt.setString(2, username);

                stmt.executeUpdate();
            
        } catch (Exception e) {
        }
    }

    
}
