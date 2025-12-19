package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.DTO.CartDto;
import com.ecommerce.project.DTO.CartItemDto;
import com.ecommerce.project.DAO.CartDAO;
import com.ecommerce.project.service.Interface.ICartService;
import com.ecommerce.project.helper.AuthHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for shopping cart management
 * Handles cart operations like adding items, updating quantities, and syncing guest carts
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    private final CartDAO cartDAO;
    private final AuthHelper authHelper;
    private final ICartService ICartService;


    public ShoppingCartController(CartDAO cartDAO, AuthHelper authHelper, ICartService ICartService) {
        this.cartDAO = cartDAO;
        this.authHelper = authHelper;
        this.ICartService = ICartService;
    }

    /**
     * Get all shopping carts
     * Endpoint: GET /api/admin/carts
     * Admin only - returns all carts in system
     */
    @GetMapping("/admin/carts")
    public ResponseEntity<?> getAllCarts() {
        List<CartDto> allCarts = ICartService.getAllCarts();
        return ResponseEntity.ok(allCarts);
    }

    /**
     * Get current user's cart
     * Endpoint: GET /api/cart
     * Returns logged-in user's cart with all items
     */
    @GetMapping("/cart")
    public ResponseEntity<?> getCartByCartId() {
        String userEmail = authHelper.loggedInEmail();
        Cart userCart = cartDAO.fetchCartByUserEmail(userEmail);
        Long userCartId = userCart.getCartId();
        CartDto userCartData = ICartService.getCart(userEmail, userCartId);
        return ResponseEntity.ok(userCartData);
    }

    /**
     * Sync guest cart to logged-in user's cart
     * Endpoint: POST /api/cart/sync
     * Used when user logs in with items in guest cart
     */
    @PostMapping("/cart/sync")
    public ResponseEntity<?> createOrUpdateCart(@RequestBody List<CartItemDto> cartItems) {
        String result = ICartService.createOrUpdateCartWithItems(cartItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Add a product to cart
     * Endpoint: POST /api/cart/items?productId=1&quantity=2
     * Creates cart if doesn't exist, validates stock availability
     */
    @PostMapping("/cart/items")
    public ResponseEntity<?> addProductToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        CartDto cartData = ICartService.addProductToCart(productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartData);
    }

    /**
     * Update product quantity in cart
     * Endpoint: PUT /api/cart/items/{productId}?action=increase|decrease
     * Increases or decreases quantity by 1
     */
    @PutMapping("/cart/items/{productId}")
    public ResponseEntity<?> updateCartProduct(@PathVariable Long productId, @RequestParam String action) {
        int quantityModifier = action.equalsIgnoreCase("decrease") ? -1 : 1;
        CartDto updatedCart = ICartService.updateProductQuantityInCart(productId, quantityModifier);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Remove a product from cart
     * Endpoint: DELETE /api/cart/items/{productId}
     * Removes product completely from cart
     */
    @DeleteMapping("/cart/items/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long productId) {
        String userEmail = authHelper.loggedInEmail();
        Cart userCart = cartDAO.fetchCartByUserEmail(userEmail);
        String deleteStatus = ICartService.deleteProductFromCart(userCart.getCartId(), productId);
        return ResponseEntity.ok(deleteStatus);
    }
}
