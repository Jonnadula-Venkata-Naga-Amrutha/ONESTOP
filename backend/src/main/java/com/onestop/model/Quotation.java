package com.onestop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Quotation {

    // Quotation details
    private int quotationId;
    private int bookingId;
    private int providerId;
    private BigDecimal quotedPrice;
    private String message;
    private String quotationStatus;
    private Timestamp createdAt;

    // Empty constructor
    public Quotation() {
    }

    // Constructor for creating a quotation
    public Quotation(int bookingId, int providerId,
                     BigDecimal quotedPrice, String message) {

        this.bookingId = bookingId;
        this.providerId = providerId;
        this.quotedPrice = quotedPrice;
        this.message = message;
    }

    // Getters and setters
    public int getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(int quotationId) {
        this.quotationId = quotationId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public BigDecimal getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(BigDecimal quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}