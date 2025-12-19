package com.ecommerce.project.DTO;

// OrderItemDto - data transfer object for individual item in an order
public class OrderItemDto {
    // Unique identifier for the order item
    private Long orderItemId;
    // Product details
    private ProductDTO product;
    // Quantity ordered
    private Integer quantity;
    // Price at the time of order (may differ from current price)
    private double orderedProductPrice;

    // Default constructor
    public OrderItemDto() {
    }

    // Constructor with all fields
    public OrderItemDto(Long orderItemId, ProductDTO product, Integer quantity, double orderedProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.orderedProductPrice = orderedProductPrice;
    }

    // Get order item ID
    public Long getOrderItemId() {
        return orderItemId;
    }

    // Set order item ID
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    // Get product details
    public ProductDTO getProduct() {
        return product;
    }

    // Set product details
    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    // Get quantity
    public Integer getQuantity() {
        return quantity;
    }

    // Set quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Get ordered product price
    public double getOrderedProductPrice() {
        return orderedProductPrice;
    }

    // Set ordered product price
    public void setOrderedProductPrice(double orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }
}
