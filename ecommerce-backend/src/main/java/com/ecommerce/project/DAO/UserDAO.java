package com.ecommerce.project.DAO;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO (Data Access Object) for User entity.
 * Handles all database operations for user accounts and authentication.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     * Returns Optional because the username might not exist.
     */
    Optional<User> findByUserName(String username);

    /**
     * Checks if a username is already taken.
     * Used during registration to prevent duplicate usernames.
     */
    Boolean existsByUserName(String username);

    /**
     * Checks if an email is already registered.
     * Used during registration to prevent duplicate emails.
     */
    Boolean existsByEmail(String email);

    /**
     * Finds all users with a specific role (e.g., ADMIN, USER, SELLER).
     * Returns results in pages for better performance.
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = :role")
    Page<User> findByRoleName(@Param("role") AppRole role, Pageable pageable);

}
