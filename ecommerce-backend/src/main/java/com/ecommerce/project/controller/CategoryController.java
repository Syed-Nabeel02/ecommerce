package com.ecommerce.project.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.project.service.Interface.ICategoryService;
import com.ecommerce.project.DTO.CategoryDto;
import com.ecommerce.project.DTO.CategoryResponse;
import com.ecommerce.project.config.Constants;

/**
 * Controller for category management
 * Handles CRUD operations for product categories
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final ICategoryService ICategoryService;

    public CategoryController(ICategoryService ICategoryService) {
        this.ICategoryService = ICategoryService;
    }

    /**
     * Create a new category
     * Endpoint: POST /api/admin/categories
     * Admin only - validates category name is unique
     */
    @PostMapping("/admin/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDTO) {
        CategoryDto newCategory = ICategoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    /**
     * Get all categories
     * Endpoint: GET /api/categories
     * Public access - supports pagination and sorting
     */
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.category_sortBy, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder) {
        CategoryResponse categoryList = ICategoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categoryList);
    }

    /**
     * Update an existing category
     * Endpoint: PUT /api/admin/categories/{categoryId}
     * Admin only - updates category details
     */
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<?> categoryUpdate(@Valid @RequestBody CategoryDto categoryDTO, @PathVariable Long categoryId) {
        CategoryDto modifiedCategory = ICategoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.ok(modifiedCategory);
    }

    /**
     * Delete a category
     * Endpoint: DELETE /api/admin/categories/{categoryId}
     * Admin only - removes category from database
     */
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<?> removeCategory(@PathVariable Long categoryId) {
        CategoryDto removedCategory = ICategoryService.removeCategory(categoryId);
        return ResponseEntity.ok(removedCategory);
    }

}
