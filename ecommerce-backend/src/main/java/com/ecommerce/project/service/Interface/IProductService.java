package com.ecommerce.project.service.Interface;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service interface for product operations
 * Handles product CRUD, search, filtering, and image management
 */
public interface IProductService {
    // Add a new product to a category
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    // Get all products with optional filters (keyword, category, model) and pagination
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword, String category, String model);

    // Search products by category ID with pagination
    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Search products by keyword in product name
    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Update existing product details
    ProductDTO updateProduct(Long productId, ProductDTO product);

    // Delete a product (removes from all carts first)
    ProductDTO deleteProduct(Long productId);

    // Upload/update product image
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

    // Get all products for admin dashboard (no filters)
    ProductResponse getAllProductsForAdmin(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

}
