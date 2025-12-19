package com.ecommerce.project.DTO;

// AnalyticsResponse - data transfer object for admin dashboard analytics data
public class AnalyticsResponse {
    // Total number of products in store
    private String productCount;
    // Total revenue from all orders
    private String totalRevenue;
    // Total number of orders placed
    private String totalOrders;

    // Default constructor
    public AnalyticsResponse() {
    }

    // Constructor with all fields
    public AnalyticsResponse(String productCount, String totalRevenue, String totalOrders) {
        this.productCount = productCount;
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
    }

    // Get product count
    public String getProductCount() {
        return productCount;
    }

    // Set product count
    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    // Get total revenue
    public String getTotalRevenue() {
        return totalRevenue;
    }

    // Set total revenue
    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    // Get total orders
    public String getTotalOrders() {
        return totalOrders;
    }

    // Set total orders
    public void setTotalOrders(String totalOrders) {
        this.totalOrders = totalOrders;
    }
}
