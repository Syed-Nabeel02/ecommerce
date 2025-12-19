package com.ecommerce.project.DAO;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO (Data Access Object) for Category entity.
 * Handles all database operations for product categories.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface CategoryDAO extends JpaRepository<Category,Long> {

    /**
     * Finds a category by its name (e.g., "Electronics", "Clothing").
     * Returns null if the category doesn't exist.
     */
    Category findByCategoryName(String categoryName);
}
