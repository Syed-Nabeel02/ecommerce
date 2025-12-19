// Category entity - represents product categories like Electronics, Clothing, etc.
package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity(name = "categories")
public class Category {
    // Unique ID for each category
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    // Category name (min 5 characters)
    @NotBlank
    @Size(min = 5, message = "Must be at least 5 characters")
    private String categoryName;

    // List of products in this category
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    // Default constructor
    public Category() {
    }

    // Constructor with all fields
    public Category(Long categoryId, String categoryName, List<Product> products) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = products;
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

    // Get list of products
    public List<Product> getProducts() {
        return products;
    }

    // Set list of products
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
