// OrderItem entity - represents a single product in an order
package com.ecommerce.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    // Unique ID for each order item
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    // Product that was ordered
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Order this item belongs to
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Quantity ordered
    private Integer qty;
    // Price of product at time of order
    private double ProductPrice;

    // Default constructor
    public OrderItem() {
    }

    // Constructor with all fields
    public OrderItem(Long orderItemId, Product product, Order order, Integer qty, double ProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.order = order;
        this.qty = qty;
        this.ProductPrice = ProductPrice;
    }

    // Get order item ID
    public Long getOrderItemId() {
        return orderItemId;
    }

    // Set order item ID
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    // Get product
    public Product getProduct() {
        return product;
    }

    // Set product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Get order
    public Order getOrder() {
        return order;
    }

    // Set order
    public void setOrder(Order order) {
        this.order = order;
    }

    // Get quantity
    public Integer getQty() {
        return qty;
    }

    // Set quantity
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    // Get product price
    public double getProductPrice() {
        return ProductPrice;
    }

    // Set product price
    public void setProductPrice(double productPrice) {
        this.ProductPrice = productPrice;
    }

}