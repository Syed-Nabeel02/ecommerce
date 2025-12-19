package com.ecommerce.project.DAO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Payment;

/**
 * DAO (Data Access Object) for Payment entity.
 * Handles all database operations for payment transactions.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
@Repository
public interface PaymentDAO extends JpaRepository<Payment, Long>{

}
