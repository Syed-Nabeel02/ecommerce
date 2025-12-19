package com.ecommerce.project.DAO;

import com.ecommerce.project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) for Cart entity.
 * Handles all database operations for shopping carts.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface CartDAO extends JpaRepository<Cart, Long> {

    /**
     * Fetches a user's shopping cart by their email address.
     * Each user typically has one active cart.
     */
    @Query("SELECT c FROM Cart c WHERE c.user.email = :userEmail")
    Cart fetchCartByUserEmail(@Param("userEmail") String userEmail);

    /**
     * Fetches a specific cart for a user (verified by email and cart ID).
     * Provides extra security by ensuring the cart belongs to the user.
     */
    @Query("SELECT c FROM Cart c WHERE c.user.email = :userEmail AND c.id = :cartIdentifier")
    Cart fetchCartByUserEmailAndId(@Param("userEmail") String userEmail, @Param("cartIdentifier") Long cartIdentifier);

    /**
     * Finds all carts containing a specific product.
     * Useful when updating product price/availability to notify users.
     */
    @Query("SELECT DISTINCT c FROM Cart c INNER JOIN FETCH c.cartItems ci INNER JOIN FETCH ci.product p WHERE p.id = :productIdentifier")
    List<Cart> retrieveCartsByProduct(@Param("productIdentifier") Long productIdentifier);
}
