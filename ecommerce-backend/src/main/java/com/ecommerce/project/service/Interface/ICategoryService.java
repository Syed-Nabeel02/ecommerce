package com.ecommerce.project.service.Interface;

import com.ecommerce.project.DTO.CategoryDto;
import com.ecommerce.project.DTO.CategoryResponse;

/**
 * Service interface for category operations
 * Handles product category CRUD operations
 */
public interface ICategoryService {
    // Get all categories with pagination and sorting
    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Create a new category (validates name uniqueness)
    CategoryDto createCategory(CategoryDto categoryDTO);

    // Delete a category by ID
    CategoryDto removeCategory(Long categoryId);

    // Update an existing category
    CategoryDto updateCategory(CategoryDto categoryDTO, Long categoryId);
}
