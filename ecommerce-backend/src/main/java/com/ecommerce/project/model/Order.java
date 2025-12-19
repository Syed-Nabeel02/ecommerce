// Order entity - represents a customer order
package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    // Unique ID for each order
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // Customer email for order confirmation
    @Email
    @Column(nullable = false)
    private String email;

    // List of items in this order
    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItems = new ArrayList<>();

    // Date order was placed
    private LocalDate orderDate;

    // Payment information for this order
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // Total amount paid
    private Double totalAmount;
    // Order status (e.g., PENDING, SHIPPED, DELIVERED)
    private String orderStatus;

    // Shipping address for this order
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    // Default constructor
    public Order() {
    }

    // Constructor with all fields
    public Order(Long orderId, String email, List<OrderItem> orderItems, LocalDate orderDate, Payment payment, Double totalAmount, String orderStatus, Address address) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.payment = payment;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.address = address;
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
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // Set list of order items
    public void setOrderItems(List<OrderItem> orderItems) {
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

    // Get payment information
    public Payment getPayment() {
        return payment;
    }

    // Set payment information
    public void setPayment(Payment payment) {
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

    // Get shipping address
    public Address getAddress() {
        return address;
    }

    // Set shipping address
    public void setAddress(Address address) {
        this.address = address;
    }
}
