package com.ecommerce.project.DTO;

// APIResponse - generic data transfer object for simple API responses
public class APIResponse {
    // Response message (e.g., "Product added successfully")
    public String message;
    // Response status (true for success, false for failure)
    private boolean status;

    // Default constructor
    public APIResponse() {
    }

    // Constructor with message and status
    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    // Get response message
    public String getMessage() {
        return message;
    }

    // Set response message
    public void setMessage(String message) {
        this.message = message;
    }

    // Check if operation was successful
    public boolean isStatus() {
        return status;
    }

    // Set operation status
    public void setStatus(boolean status) {
        this.status = status;
    }
}
