package com.ecommerce.project.service.Interface;

import com.ecommerce.project.DTO.AnalyticsResponse;

/**
 * Service interface for analytics operations
 * Provides dashboard statistics for admin
 */
public interface IAnalyticsService {
    // Get analytics data (product count, total orders, total revenue)
    AnalyticsResponse getAnalytics();
}
