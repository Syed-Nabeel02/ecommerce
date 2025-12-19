// CartItem entity - represents a single product in a shopping cart
package com.ecommerce.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    // Unique ID for each cart item
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    // Cart this item belongs to
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Product added to cart
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Quantity of this product
    private Integer qty;
    // Price of product at time of adding
    private double productPrice;

    // Default constructor
    public CartItem() {
    }

    // Constructor with all fields
    public CartItem(Long cartItemId, Cart cart, Product product, Integer qty, double productPrice) {
        this.cartItemId = cartItemId;
        this.cart = cart;
        this.product = product;
        this.qty = qty;
        this.productPrice = productPrice;
    }

    // Get cart item ID
    public Long getCartItemId() {
        return cartItemId;
    }

    // Set cart item ID
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    // Get cart
    public Cart getCart() {
        return cart;
    }

    // Set cart
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    // Get product
    public Product getProduct() {
        return product;
    }

    // Set product
    public void setProduct(Product product) {
        this.product = product;
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
        return productPrice;
    }

    // Set product price
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
