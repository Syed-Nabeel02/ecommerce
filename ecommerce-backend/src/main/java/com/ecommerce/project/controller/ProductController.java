package com.ecommerce.project.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ProductResponse;
import com.ecommerce.project.config.Constants;
import com.ecommerce.project.service.Interface.IProductService;

import java.io.IOException;

/**
 * Controller for product management
 * Handles CRUD operations for products, search, and image uploads
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    private final IProductService IProductService;

    public ProductController(IProductService IProductService) {
        this.IProductService = IProductService;
    }

    /**
     * Add a new product to a category
     * Endpoint: POST /api/admin/categories/{categoryId}/products
     * Admin only
     */
    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                        @PathVariable Long categoryId) {
        ProductDTO createdProduct = IProductService.addProduct(categoryId, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Get all products in a specific category
     * Endpoint: GET /api/categories/{categoryId}/products
     * Supports pagination and sorting
     */
    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<?> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.products_sortBy, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder) {
        ProductResponse categoryProducts = IProductService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categoryProducts);
    }

    /**
     * Search products by keyword
     * Endpoint: GET /api/products/search?q=keyword
     * Searches in product names
     */
    @GetMapping("/products/search")
    public ResponseEntity<?> getProductsByKeyword(
            @RequestParam(name = "q", required = true) String keyword,
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.products_sortBy, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder) {
        ProductResponse searchResults = IProductService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(searchResults);
    }

    /**
     * Get all products with optional filtering
     * Endpoint: GET /api/products
     * Supports filters: keyword, category, model
     * Supports pagination and sorting
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.products_sortBy, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder) {
        ProductResponse fetchedProducts = IProductService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder, keyword, category, model);
        return ResponseEntity.ok(fetchedProducts);
    }

    /**
     * Update an existing product
     * Endpoint: PUT /api/admin/products/{productId}
     * Admin only - updates product details
     */
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                           @PathVariable Long productId) {
        ProductDTO modifiedProduct = IProductService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(modifiedProduct);
    }

    /**
     * Delete a product
     * Endpoint: DELETE /api/admin/products/{productId}
     * Admin only - removes product from all carts first
     */
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        ProductDTO removedProduct = IProductService.deleteProduct(productId);
        return ResponseEntity.ok(removedProduct);
    }

    /**
     * Upload/update product image
     * Endpoint: PUT /api/admin/products/{productId}/image
     * Admin only - accepts multipart file upload
     */
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<?> updateProductImage(@PathVariable Long productId,
                                                @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProductWithImage = IProductService.updateProductImage(productId, image);
        return ResponseEntity.ok(updatedProductWithImage);
    }

    /**
     * Get all products for admin dashboard
     * Endpoint: GET /api/admin/products
     * Admin only - no filters, just pagination
     */
    @GetMapping("/admin/products")
    public ResponseEntity<?> getAllProductsForAdmin(
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.products_sortBy, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder) {
        ProductResponse allAdminProducts = IProductService.getAllProductsForAdmin(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(allAdminProducts);
    }
}
