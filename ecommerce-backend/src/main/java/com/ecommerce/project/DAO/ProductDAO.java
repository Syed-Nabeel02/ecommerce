package com.ecommerce.project.DAO;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * DAO (Data Access Object) for Product entity.
 * Handles all database operations for products in the store.
 * Extends JpaRepository for CRUD and JpaSpecificationExecutor for advanced filtering.
 */
@Repository
public interface ProductDAO extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Finds all products in a category, sorted by price (lowest first).
     * Returns results in pages for better performance.
     */
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    /**
     * Searches for products by name (case-insensitive, partial match).
     * Example: searching "phone" will find "iPhone", "Samsung Phone", etc.
     */
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    /**
     * Finds all products listed by a specific seller.
     * Useful for seller dashboards to manage their inventory.
     */
    Page<Product> findByUser(User user, Pageable pageDetails);
}
