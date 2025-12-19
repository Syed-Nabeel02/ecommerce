package com.ecommerce.project.DAO;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * DAO (Data Access Object) for Role entity.
 * Handles all database operations for user roles (ADMIN, USER, SELLER).
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface RoleDAO extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name (e.g., ROLE_ADMIN, ROLE_USER).
     * Returns Optional because the role might not exist.
     */
    Optional<Role> findByRoleName(AppRole appRole);
}
