package com.ecommerce.project.controller;

import com.ecommerce.project.DTO.AnalyticsResponse;
import com.ecommerce.project.service.Interface.IAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for admin analytics
 * Provides dashboard statistics like total products, orders, and revenue
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class AnalyticsController {

    private final IAnalyticsService IAnalyticsService;

    public AnalyticsController(IAnalyticsService IAnalyticsService) {
        this.IAnalyticsService = IAnalyticsService;
    }

    /**
     * Get analytics data for admin dashboard
     * Endpoint: GET /api/admin/analytics
     * Returns product count, total orders, and total revenue
     */
    @GetMapping("/admin/analytics")
    public ResponseEntity<AnalyticsResponse> Analytics() {
        AnalyticsResponse response = IAnalyticsService.getAnalytics();
        return ResponseEntity.ok(response);
    }
}