package com.ecommerce.project.service.Interface;

import com.ecommerce.project.DTO.CartDto;
import com.ecommerce.project.DTO.CartItemDto;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for shopping cart operations
 * Handles cart management, adding/updating/removing items
 */
public interface ICartService {
    // Add a product to the current user's cart
    CartDto addProductToCart(Long productId, Integer quantity);

    // Get all carts in system (admin function)
    List<CartDto> getAllCarts();

    // Get a specific cart by email and cart ID
    CartDto getCart(String emailId, Long cartId);

    // Update product quantity in cart (increase or decrease)
    @Transactional
    CartDto updateProductQuantityInCart(Long productId, Integer quantity);

    // Remove a product from cart
    String deleteProductFromCart(Long cartId, Long productId);

    // Update cart when product price changes
    void updateProductInCarts(Long cartId, Long productId);

    // Create or update cart with items (used for guest cart sync)
    String createOrUpdateCartWithItems(List<CartItemDto> cartItems);
}
