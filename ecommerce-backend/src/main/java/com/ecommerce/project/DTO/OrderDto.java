package com.ecommerce.project.DTO;

import java.time.LocalDate;
import java.util.List;

// OrderDto - data transfer object for order information sent to frontend
public class OrderDto {
    // Unique identifier for the order
    private Long orderId;
    // Customer's email address
    private String email;
    // List of items in the order
    private List<OrderItemDto> orderItems;
    // Date when order was placed
    private LocalDate orderDate;
    // Payment details for the order
    private PaymentDTO payment;
    // Total order amount
    private Double totalAmount;
    // Current status of the order (e.g., PENDING, SHIPPED, DELIVERED)
    private String orderStatus;
    // ID of the delivery address
    private Long addressId;

    // Default constructor
    public OrderDto() {
    }

    // Constructor with all fields
    public OrderDto(Long orderId, String email, List<OrderItemDto> orderItems, LocalDate orderDate, PaymentDTO payment, Double totalAmount, String orderStatus, Long addressId) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.payment = payment;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.addressId = addressId;
    }

    // Get order ID
    public Long getOrderId() {
        return orderId;
    }

    // Set order ID
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    // Get customer email
    public String getEmail() {
        return email;
    }

    // Set customer email
    public void setEmail(String email) {
        this.email = email;
    }

    // Get list of order items
    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    // Set list of order items
    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    // Get order date
    public LocalDate getOrderDate() {
        return orderDate;
    }

    // Set order date
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    // Get payment details
    public PaymentDTO getPayment() {
        return payment;
    }

    // Set payment details
    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    // Get total amount
    public Double getTotalAmount() {
        return totalAmount;
    }

    // Set total amount
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Get order status
    public String getOrderStatus() {
        return orderStatus;
    }

    // Set order status
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    // Get delivery address ID
    public Long getAddressId() {
        return addressId;
    }

    // Set delivery address ID
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
