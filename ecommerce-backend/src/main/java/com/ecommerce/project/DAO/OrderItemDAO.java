package com.ecommerce.project.DAO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.OrderItem;

/**
 * DAO (Data Access Object) for OrderItem entity.
 * Handles all database operations for individual items within orders.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Long> {

}
