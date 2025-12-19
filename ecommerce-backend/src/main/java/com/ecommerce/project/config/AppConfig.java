package com.ecommerce.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for application-wide settings.
 * Provides ModelMapper for converting between entities and DTOs,
 * plus default pagination and sorting constants.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a ModelMapper bean for converting entities to DTOs and vice versa.
     * This helps keep entity and API layers separate.
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    // Default pagination settings
    public static final String page_num = "0";         // Start from first page
    public static final String page_size = "10";       // 10 items per page

    // Default sorting fields for different entities
    public static final String category_sortBy = "categoryId";
    public static final String products_sortBy = "productId";
    public static final String sort_order = "asc";     // Ascending order
    public static final String orders_sort_by = "totalAmount";
    public static final String users_sort_by = "userId";
}
