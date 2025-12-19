package com.ecommerce.project.config;

/**
 * Constants class storing application-wide default values.
 * Defines default pagination and sorting settings for API responses.
 * These values are used when clients don't specify their own preferences.
 */
public class Constants {
    // Default sorting order for lists (ascending)
    public static final String sort_order = "asc";

    // Default pagination settings
    public static final String page_num = "0";          // Start from first page
    public static final String page_size = "12";        // 12 items per page

    // Default sorting fields for different entities
    public static final String orders_sort_by = "totalAmount";
    public static final String users_sort_by = "userId";
    public static final String category_sortBy = "categoryId";
    public static final String products_sortBy = "productId";

}
