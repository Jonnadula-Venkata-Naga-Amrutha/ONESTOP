package com.onestop.model;

import java.sql.Time;

public class Availability {

    // Database fields
    private int availabilityId;
    private int providerId;
    private String availableDay;
    private Time startTime;
    private Time endTime;
    private boolean isAvailable;

    // Default constructor
    public Availability() {
    }

    // Constructor for adding availability
    public Availability(int providerId, String availableDay,
                        Time startTime, Time endTime,
                        boolean isAvailable) {

        this.providerId = providerId;
        this.availableDay = availableDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public int getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
} 
