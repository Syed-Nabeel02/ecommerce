package com.ecommerce.project.service.Interface;

import com.ecommerce.project.DTO.OrderDto;
import com.ecommerce.project.DTO.OrderResponse;
import jakarta.transaction.Transactional;

/**
 * Service interface for order operations
 * Handles order placement, tracking, and status management
 */
public interface IOrderService {
    // Place a new order from cart (processes payment, reduces inventory, clears cart)
    @Transactional
    OrderDto placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);

    // Get all orders in system with pagination
    OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Update order status (e.g., Processing, Shipped, Delivered)
    OrderDto orderUpdate(Long orderId, String status);

    // Get orders for a specific user by email
    OrderResponse getUserOrders(String emailId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Get orders for a specific user by user ID
    OrderResponse getUserOrdersByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

}
