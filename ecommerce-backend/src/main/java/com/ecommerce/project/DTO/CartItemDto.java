package com.ecommerce.project.DTO;

// CartItemDto - data transfer object for individual cart item (used when adding/updating items)
public class CartItemDto {
    // ID of the product to add to cart
    private Long productId;
    // Quantity of the product
    private Integer quantity;

    // Default constructor
    public CartItemDto() {
    }

    // Constructor with all fields
    public CartItemDto(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Get product ID
    public Long getProductId() {
        return productId;
    }

    // Set product ID
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // Get quantity
    public Integer getQuantity() {
        return quantity;
    }

    // Set quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
