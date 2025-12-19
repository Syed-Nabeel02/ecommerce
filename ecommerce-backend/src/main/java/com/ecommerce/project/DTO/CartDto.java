package com.ecommerce.project.DTO;

import java.util.ArrayList;
import java.util.List;

// CartDto - data transfer object for shopping cart information
public class CartDto {
    // Unique identifier for the cart
    private Long cartId;
    // Total price of all items in the cart
    private Double totalPrice = 0.0;
    // List of products in the cart
    private List<ProductDTO> products = new ArrayList<>();

    // Default constructor
    public CartDto() {
    }

    // Constructor with all fields
    public CartDto(Long cartId, Double totalPrice, List<ProductDTO> products) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    // Get cart ID
    public Long getCartId() {
        return cartId;
    }

    // Set cart ID
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    // Get total price
    public Double getTotalPrice() {
        return totalPrice;
    }

    // Set total price
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Get list of products
    public List<ProductDTO> getProducts() {
        return products;
    }

    // Set list of products
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
