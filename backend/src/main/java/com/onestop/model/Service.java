package com.onestop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Service {

    // Service details
    private int serviceId;
    private int providerId;
    private int categoryId;
    private String serviceName;
    private String description;
    private BigDecimal basePrice;
    private String serviceStatus;
    private Timestamp createdAt;

    // Empty constructor
    public Service() {
    }

    // Constructor for creating a service
    public Service(int providerId, int categoryId, String serviceName,
                   String description, BigDecimal basePrice) {

        this.providerId = providerId;
        this.categoryId = categoryId;
        this.serviceName = serviceName;
        this.description = description;
        this.basePrice = basePrice;
    }

    // Getters and setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}