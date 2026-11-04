package com.onestop.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {

    // Booking details
    private int bookingId;
    private int customerId;
    private int providerId;
    private int serviceId;
    private String serviceAddress;
    private Date serviceDate;
    private Time preferredTime;
    private String problemDescription;
    private String bookingStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Empty constructor
    public Booking() {
    }

    // Constructor for creating a new booking
    public Booking(int customerId, int providerId, int serviceId,
                   String serviceAddress, Date serviceDate,
                   Time preferredTime, String problemDescription) {

        this.customerId = customerId;
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.serviceAddress = serviceAddress;
        this.serviceDate = serviceDate;
        this.preferredTime = preferredTime;
        this.problemDescription = problemDescription;
    }

    // Getters and setters
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

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Time getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(Time preferredTime) {
        this.preferredTime = preferredTime;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}