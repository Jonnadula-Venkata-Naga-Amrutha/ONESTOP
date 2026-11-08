package com.onestop.dao;

import com.onestop.config.DBConnection;
import com.onestop.model.Availability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityDAO {

    // Add provider availability
    public int addAvailability(Availability availability)
            throws SQLException {

        String sql = "INSERT INTO provider_availability "
                + "(provider_id, available_day, start_time, end_time, is_available) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, availability.getProviderId());
            ps.setString(2, availability.getAvailableDay());
            ps.setTime(3, availability.getStartTime());
            ps.setTime(4, availability.getEndTime());
            ps.setBoolean(5, availability.isAvailable());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Availability creation failed.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("Availability ID was not generated.");
        }
    }

    // Get availability of a provider
    public List<Availability> getProviderAvailability(int providerId)
            throws SQLException {

        String sql = "SELECT * FROM provider_availability "
                + "WHERE provider_id = ?";

        List<Availability> availabilityList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, providerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    availabilityList.add(mapAvailability(rs));
                }
            }
        }

        return availabilityList;
    }

    // Update availability
    public boolean updateAvailability(Availability availability)
            throws SQLException {

        String sql = "UPDATE provider_availability "
                + "SET available_day = ?, start_time = ?, "
                + "end_time = ?, is_available = ? "
                + "WHERE availability_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, availability.getAvailableDay());
            ps.setTime(2, availability.getStartTime());
            ps.setTime(3, availability.getEndTime());
            ps.setBoolean(4, availability.isAvailable());
            ps.setInt(5, availability.getAvailabilityId());

            return ps.executeUpdate() > 0;
        }
    }

    // Delete availability
    // Mark availability as unavailable
public boolean deleteAvailability(int availabilityId)
        throws SQLException {

    String sql = "UPDATE provider_availability "
            + "SET is_available = FALSE "
            + "WHERE availability_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, availabilityId);

        return ps.executeUpdate() > 0;
    }
}

    // Convert database row into Availability object
    private Availability mapAvailability(ResultSet rs)
            throws SQLException {

        Availability availability = new Availability();

        availability.setAvailabilityId(rs.getInt("availability_id"));
        availability.setProviderId(rs.getInt("provider_id"));
        availability.setAvailableDay(rs.getString("available_day"));
        availability.setStartTime(rs.getTime("start_time"));
        availability.setEndTime(rs.getTime("end_time"));
        availability.setAvailable(rs.getBoolean("is_available"));

        return availability;
    }
}