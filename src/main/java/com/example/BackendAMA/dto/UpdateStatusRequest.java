package com.example.BackendAMA.dto;

public class UpdateStatusRequest {
    private String serviceStatusNumber;
    private String status;

    // Getters and Setters
    public String getServiceStatusNumber() {
        return serviceStatusNumber;
    }

    public void setServiceStatusNumber(String serviceStatusNumber) {
        this.serviceStatusNumber = serviceStatusNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
