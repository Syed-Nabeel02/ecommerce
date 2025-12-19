package com.ecommerce.project.DAO;

import com.ecommerce.project.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * DAO (Data Access Object) for CartItem entity.
 * Handles all database operations for individual items in shopping carts.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface CartItemDAO extends JpaRepository<CartItem, Long> {

    /**
     * Finds a specific product in a specific cart.
     * Useful for checking if a product is already in the cart before adding.
     */
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartIdentifier AND ci.product.id = :productIdentifier")
    CartItem retrieveCartItemByProductAndCart(@Param("cartIdentifier") Long cartIdentifier, @Param("productIdentifier") Long productIdentifier);

    /**
     * Removes a specific product from a cart.
     * @Modifying indicates this query changes data in the database.
     */
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartIdentifier AND ci.product.id = :productIdentifier")
    void removeCartItemByProductAndCart(@Param("cartIdentifier") Long cartIdentifier, @Param("productIdentifier") Long productIdentifier);

    /**
     * Removes all items from a cart (e.g., after checkout).
     * @Modifying indicates this query changes data in the database.
     */
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartIdentifier")
    void removeAllItemsByCart(@Param("cartIdentifier") Long cartIdentifier);
}