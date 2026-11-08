package com.onestop.dao;

import com.onestop.config.DBConnection;
import com.onestop.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Create a new booking
    public int createBooking(Booking booking) throws SQLException {

        String sql = "INSERT INTO bookings "
                + "(customer_id, provider_id, service_id, service_address, "
                + "service_date, preferred_time, problem_description, booking_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'PENDING')";

        String historySql = "INSERT INTO booking_status_history "
                + "(booking_id, old_status, new_status, changed_by, remarks) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int bookingId;

                // Insert the booking
                try (PreparedStatement ps = con.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, booking.getCustomerId());
                    ps.setInt(2, booking.getProviderId());
                    ps.setInt(3, booking.getServiceId());
                    ps.setString(4, booking.getServiceAddress());
                    ps.setDate(5, booking.getServiceDate());
                    ps.setTime(6, booking.getPreferredTime());
                    ps.setString(7, booking.getProblemDescription());

                    int rows = ps.executeUpdate();

                    if (rows == 0) {
                        throw new SQLException("Booking creation failed.");
                    }

                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (rs.next()) {
                            bookingId = rs.getInt(1);
                        } else {
                            throw new SQLException(
                                    "Booking ID was not generated.");
                        }
                    }
                }

                // Record the initial booking status
                try (PreparedStatement ps =
                             con.prepareStatement(historySql)) {

                    ps.setInt(1, bookingId);
                    ps.setNull(2, Types.VARCHAR);
                    ps.setString(3, "PENDING");
                    ps.setInt(4, booking.getCustomerId());
                    ps.setString(5, "Booking created.");

                    ps.executeUpdate();
                }

                // Save booking and history together
                con.commit();

                return bookingId;

            } catch (SQLException e) {

                con.rollback();
                throw e;
            }
        }
    }

    // Get booking by ID
    public Booking getBookingById(int bookingId) throws SQLException {

        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapBooking(rs);
                }
            }
        }

        return null;
    }

    // Get all bookings made by a customer
    public List<Booking> getBookingsByCustomerId(int customerId)
            throws SQLException {

        String sql = "SELECT * FROM bookings WHERE customer_id = ? "
                + "ORDER BY created_at DESC";

        List<Booking> bookings = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    bookings.add(mapBooking(rs));
                }
            }
        }

        return bookings;
    }

    // Get all bookings assigned to a provider
    public List<Booking> getBookingsByProviderId(int providerId)
            throws SQLException {

        String sql = "SELECT * FROM bookings WHERE provider_id = ? "
                + "ORDER BY created_at DESC";

        List<Booking> bookings = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, providerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    bookings.add(mapBooking(rs));
                }
            }
        }

        return bookings;
    }

    // Update booking status and record its history
    public boolean updateBookingStatus(int bookingId, String status,
                                       int changedBy, String remarks)
            throws SQLException {

        String selectSql = "SELECT booking_status FROM bookings "
                + "WHERE booking_id = ? FOR UPDATE";

        String updateSql = "UPDATE bookings SET booking_status = ? "
                + "WHERE booking_id = ?";

        String historySql = "INSERT INTO booking_status_history "
                + "(booking_id, old_status, new_status, changed_by, remarks) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                String oldStatus;

                // Get and lock the current booking status
                try (PreparedStatement ps =
                             con.prepareStatement(selectSql)) {

                    ps.setInt(1, bookingId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        oldStatus = rs.getString("booking_status");
                    }
                }

                // Prevent duplicate status updates
                if (oldStatus.equals(status)) {
                    con.rollback();
                    return false;
                }

                // Update booking status
                                try (PreparedStatement ps =
                             con.prepareStatement(updateSql)) {

                    ps.setString(1, status);
                    ps.setInt(2, bookingId);

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException(
                                "Booking status update failed.");
                    }
                }

                // Record the status change in history
                try (PreparedStatement ps =
                             con.prepareStatement(historySql)) {

                    ps.setInt(1, bookingId);
                    ps.setString(2, oldStatus);
                    ps.setString(3, status);
                    ps.setInt(4, changedBy);
                    ps.setString(5, remarks);

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException(
                                "Booking history insertion failed.");
                    }
                }

                // Save both operations together
                con.commit();

                return true;

            } catch (SQLException e) {

                con.rollback();
                throw e;
            }
        }
    }

    // Cancel a booking
    public boolean cancelBooking(int bookingId, int changedBy,
                                 String remarks) throws SQLException {

        return updateBookingStatus(
                bookingId, "CANCELLED", changedBy, remarks);
    }

    // Convert database row into Booking object
    private Booking mapBooking(ResultSet rs) throws SQLException {

        Booking booking = new Booking();

        booking.setBookingId(rs.getInt("booking_id"));
        booking.setCustomerId(rs.getInt("customer_id"));
        booking.setProviderId(rs.getInt("provider_id"));
        booking.setServiceId(rs.getInt("service_id"));
        booking.setServiceAddress(rs.getString("service_address"));
        booking.setServiceDate(rs.getDate("service_date"));
        booking.setPreferredTime(rs.getTime("preferred_time"));
        booking.setProblemDescription(
                rs.getString("problem_description"));
        booking.setBookingStatus(rs.getString("booking_status"));
        booking.setCreatedAt(rs.getTimestamp("created_at"));
        booking.setUpdatedAt(rs.getTimestamp("updated_at"));

        return booking;
    }
}