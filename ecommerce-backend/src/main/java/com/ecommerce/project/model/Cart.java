// Cart entity - represents a user's shopping cart
package com.ecommerce.project.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    // Unique ID for each cart
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    // List of items in the cart
    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    // User who owns this cart
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Total price of all items in cart
    private Double totalPrice = 0.0;

    // Default constructor
    public Cart() {
    }

    // Constructor with all fields
    public Cart(Long cartId, User user, List<CartItem> cartItems, Double totalPrice) {
        this.cartId = cartId;
        this.user = user;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    // Get cart ID
    public Long getCartId() {
        return cartId;
    }

    // Set cart ID
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    // Get user who owns cart
    public User getUser() {
        return user;
    }

    // Set user who owns cart
    public void setUser(User user) {
        this.user = user;
    }

    // Get list of cart items
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // Set list of cart items
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    // Get total price
    public Double getTotalPrice() {
        return totalPrice;
    }

    // Set total price
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
