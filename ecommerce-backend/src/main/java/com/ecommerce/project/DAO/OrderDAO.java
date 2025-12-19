package com.ecommerce.project.DAO;

import com.ecommerce.project.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * DAO (Data Access Object) for Order entity.
 * Handles all database operations for customer orders.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface OrderDAO extends JpaRepository<Order, Long> {

    /**
     * Fetches all orders placed by a specific user (identified by email).
     * Returns results in pages for better performance.
     */
    @Query("SELECT o FROM Order o WHERE o.email = :userEmailAddress")
    Page<Order> fetchOrdersByUserEmail(@Param("userEmailAddress") String userEmailAddress, Pageable pageable);

    /**
     * Calculates the total revenue from all orders in the system.
     * Returns 0.0 if there are no orders.
     */
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0.0) FROM Order o")
    Double calculateTotalRevenue();
}