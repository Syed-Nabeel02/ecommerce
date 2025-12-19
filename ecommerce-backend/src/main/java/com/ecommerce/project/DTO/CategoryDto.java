package com.ecommerce.project.DTO;

// CategoryDto - data transfer object for product category information
public class CategoryDto {
    // Unique identifier for the category
    private Long categoryId;
    // Name of the category (e.g., Electronics, Clothing)
    private String categoryName;

    // Default constructor
    public CategoryDto() {
    }

    // Constructor with all fields
    public CategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Get category ID
    public Long getCategoryId() {
        return categoryId;
    }

    // Set category ID
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    // Get category name
    public String getCategoryName() {
        return categoryName;
    }

    // Set category name
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
