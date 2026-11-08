package com.onestop.dao;

import com.onestop.config.DBConnection;
import com.onestop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class UserDAO {

    // Register a new user and return the generated user ID
    public int registerUser(User user) throws SQLException {

        String sql = "INSERT INTO users "
                + "(full_name, email, phone, password_hash, role) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new SQLException("User registration failed.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("User ID was not generated.");
        }
    }

    // Find a user using email
    public User getUserByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    // Check whether an email already exists
    public boolean emailExists(String email) throws SQLException {

        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Find a user using user ID
    public User getUserById(int userId) throws SQLException {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    // Convert database result into a User object
    private User mapUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));

        return user;
    }
}
