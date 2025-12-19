package com.ecommerce.project.DTO;

// OrderStatusDto - data transfer object for updating order status
public class OrderStatusDto {
    // New status for the order (e.g., SHIPPED, DELIVERED, CANCELLED)
    private String status;

    // Default constructor
    public OrderStatusDto() {
    }

    // Get order status
    public String getStatus() {
        return status;
    }

    // Set order status
    public void setStatus(String status) {
        this.status = status;
    }
}
