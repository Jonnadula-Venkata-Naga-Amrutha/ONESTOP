package com.onestop.dao;

import com.onestop.config.DBConnection;
import com.onestop.model.Quotation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuotationDAO {

    // Create a new quotation
    public int createQuotation(Quotation quotation) throws SQLException {

        String sql = "INSERT INTO quotations "
                + "(booking_id, provider_id, quoted_price, message, quotation_status) "
                + "VALUES (?, ?, ?, ?, 'PENDING')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, quotation.getBookingId());
            ps.setInt(2, quotation.getProviderId());
            ps.setBigDecimal(3, quotation.getQuotedPrice());
            ps.setString(4, quotation.getMessage());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Quotation creation failed.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("Quotation ID was not generated.");
        }
    }

    // Get quotation by ID
    public Quotation getQuotationById(int quotationId)
            throws SQLException {

        String sql = "SELECT * FROM quotations WHERE quotation_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quotationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapQuotation(rs);
                }
            }
        }

        return null;
    }

    // Get all quotations for a booking
    public List<Quotation> getQuotationsByBookingId(int bookingId)
            throws SQLException {

        String sql = "SELECT * FROM quotations "
                + "WHERE booking_id = ? "
                + "ORDER BY created_at DESC";

        List<Quotation> quotations = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    quotations.add(mapQuotation(rs));
                }
            }
        }

        return quotations;
    }

    // Accept a quotation and reject other pending quotations
    public boolean acceptQuotation(int quotationId) throws SQLException {

        String selectSql = "SELECT booking_id, quotation_status "
                + "FROM quotations WHERE quotation_id = ? FOR UPDATE";

        String acceptSql = "UPDATE quotations "
                + "SET quotation_status = 'ACCEPTED' "
                + "WHERE quotation_id = ? AND quotation_status = 'PENDING'";

        String rejectOthersSql = "UPDATE quotations "
                + "SET quotation_status = 'REJECTED' "
                + "WHERE booking_id = ? "
                + "AND quotation_id <> ? "
                + "AND quotation_status = 'PENDING'";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int bookingId;

                // Lock and check the selected quotation
                try (PreparedStatement ps = con.prepareStatement(selectSql)) {

                    ps.setInt(1, quotationId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        if (!"PENDING".equals(rs.getString("quotation_status"))) {
                            con.rollback();
                            return false;
                        }

                        bookingId = rs.getInt("booking_id");
                    }
                }

                // Accept the selected quotation
                try (PreparedStatement ps = con.prepareStatement(acceptSql)) {

                    ps.setInt(1, quotationId);

                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }
                }

                // Reject other pending quotations for the same booking
                try (PreparedStatement ps =
                             con.prepareStatement(rejectOthersSql)) {

                    ps.setInt(1, bookingId);
                    ps.setInt(2, quotationId);

                    ps.executeUpdate();
                }

                // Save all changes together
                con.commit();

                return true;

            } catch (SQLException e) {

                con.rollback();
                throw e;
            }
        }
    }

    // Reject a pending quotation
    public boolean rejectQuotation(int quotationId) throws SQLException {

        String sql = "UPDATE quotations "
                + "SET quotation_status = 'REJECTED' "
                + "WHERE quotation_id = ? "
                + "AND quotation_status = 'PENDING'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quotationId);

            return ps.executeUpdate() > 0;
        }
    }

    // Convert database row into Quotation object
    private Quotation mapQuotation(ResultSet rs) throws SQLException {

        Quotation quotation = new Quotation();

        quotation.setQuotationId(rs.getInt("quotation_id"));
        quotation.setBookingId(rs.getInt("booking_id"));
        quotation.setProviderId(rs.getInt("provider_id"));
        quotation.setQuotedPrice(rs.getBigDecimal("quoted_price"));
        quotation.setMessage(rs.getString("message"));
        quotation.setQuotationStatus(rs.getString("quotation_status"));
        quotation.setCreatedAt(rs.getTimestamp("created_at"));

        return quotation;
    }
}