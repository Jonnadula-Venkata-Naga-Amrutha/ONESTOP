package com.onestop.model;

import java.sql.Timestamp;

public class Review {

    // Review details
    private int reviewId;
    private int bookingId;
    private int customerId;
    private int providerId;
    private int rating;
    private String reviewComment;
    private Timestamp createdAt;

    // Empty constructor
    public Review() {
    }

    // Constructor for adding a review
    public Review(int bookingId, int customerId, int providerId,
                  int rating, String reviewComment) {

        this.bookingId = bookingId;
        this.customerId = customerId;
        this.providerId = providerId;
        this.rating = rating;
        this.reviewComment = reviewComment;
    }

    // Getters and setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}