package com.ecommerce.project.DAO;

import com.ecommerce.project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO (Data Access Object) for Address entity.
 * Handles all database operations for customer shipping/billing addresses.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface AddressDAO extends JpaRepository<Address, Long> {
}
