package com.onestop.dao;

import com.onestop.config.DBConnection;
import com.onestop.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Add a new review
    public int addReview(Review review) throws SQLException {

        String sql = "INSERT INTO reviews "
                + "(booking_id, customer_id, provider_id, rating, review_comment) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getBookingId());
            ps.setInt(2, review.getCustomerId());
            ps.setInt(3, review.getProviderId());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getReviewComment());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Review creation failed.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("Review ID was not generated.");
        }
    }

    // Get all reviews for a provider
    public List<Review> getReviewsByProviderId(int providerId)
            throws SQLException {

        String sql = "SELECT * FROM reviews "
                + "WHERE provider_id = ? "
                + "ORDER BY created_at DESC";

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, providerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    reviews.add(mapReview(rs));
                }
            }
        }

        return reviews;
    }

    // Get review for a particular booking
    public Review getReviewByBookingId(int bookingId)
            throws SQLException {

        String sql = "SELECT * FROM reviews WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapReview(rs);
                }
            }
        }

        return null;
    }

    // Convert database row into Review object
    private Review mapReview(ResultSet rs) throws SQLException {

        Review review = new Review();

        review.setReviewId(rs.getInt("review_id"));
        review.setBookingId(rs.getInt("booking_id"));
        review.setCustomerId(rs.getInt("customer_id"));
        review.setProviderId(rs.getInt("provider_id"));
        review.setRating(rs.getInt("rating"));
        review.setReviewComment(rs.getString("review_comment"));
        review.setCreatedAt(rs.getTimestamp("created_at"));

        return review;
    }
}