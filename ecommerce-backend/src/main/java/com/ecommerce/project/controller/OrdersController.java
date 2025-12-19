package com.ecommerce.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.project.DTO.*;
import com.ecommerce.project.config.Constants;
import com.ecommerce.project.helper.AuthHelper;
import com.ecommerce.project.service.Interface.IOrderService;

/**
 * Controller for order management
 * Handles order placement, tracking, and status updates
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class OrdersController {

    private final IOrderService IOrderService;
    private final AuthHelper authHelper;

    public OrdersController(IOrderService IOrderService, AuthHelper authHelper) {
        this.IOrderService = IOrderService;
        this.authHelper = authHelper;
    }

    /**
     * Place a new order
     * Endpoint: POST /api/orders
     * Creates order from current user's cart, processes payment, reduces inventory
     */
    @PostMapping("/orders")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequestDTO) {
        String userEmail = authHelper.loggedInEmail();

        String paymentMethodValue = orderRequestDTO.getPaymentMethod() != null
                ? orderRequestDTO.getPaymentMethod()
                : "Cash on Delivery";

        String pgNameValue = orderRequestDTO.getPgName() != null
                ? orderRequestDTO.getPgName()
                : "None";

        String pgPaymentIdValue = orderRequestDTO.getPgPaymentId() != null
                ? orderRequestDTO.getPgPaymentId()
                : "N/A";

        String pgStatusValue = orderRequestDTO.getPgStatus() != null
                ? orderRequestDTO.getPgStatus()
                : "Pending";

        String pgResponseMessageValue = orderRequestDTO.getPgResponseMessage() != null
                ? orderRequestDTO.getPgResponseMessage()
                : "Order successfull";

        OrderDto createdOrder = IOrderService.placeOrder(
                userEmail,
                orderRequestDTO.getAddressId(),
                paymentMethodValue,
                pgNameValue,
                pgPaymentIdValue,
                pgStatusValue,
                pgResponseMessageValue
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Get all orders in the system
     * Endpoint: GET /api/admin/orders
     * Admin only - supports pagination and sorting
     */
    @GetMapping("/admin/orders")
    public ResponseEntity<?> getOrders(
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.orders_sort_by, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder
    ) {
        OrderResponse allOrdersData = IOrderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(allOrdersData);
    }

    /**
     * Update order status
     * Endpoint: PUT /api/admin/orders/{orderId}/status
     * Admin only - changes order status (e.g., Processing, Shipped, Delivered)
     */
    @PutMapping("/admin/orders/{orderId}/status")
    public ResponseEntity<?> updateOrders(
            @PathVariable Long orderId,
            @RequestBody OrderStatusDto orderStatusDto) {
        OrderDto modifiedOrder = IOrderService.orderUpdate(orderId, orderStatusDto.getStatus());
        return ResponseEntity.ok(modifiedOrder);
    }

    /**
     * Get current user's orders
     * Endpoint: GET /api/orders
     * Returns all orders for the logged-in user with pagination
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getOrderFromUser(
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.orders_sort_by, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder
    ) {
        String userEmail = authHelper.loggedInEmail();
        OrderResponse userOrdersData = IOrderService.getUserOrders(userEmail, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(userOrdersData);
    }

    /**
     * Get orders for a specific user by user ID
     * Endpoint: GET /api/admin/users/{userId}/orders
     * Admin only - view any user's order history
     */
    @GetMapping("/admin/users/{userId}/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = Constants.page_size, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.orders_sort_by, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = Constants.sort_order, required = false) String sortOrder
    ) {
        OrderResponse userOrdersByIdData = IOrderService.getUserOrdersByUserId(userId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(userOrdersByIdData);
    }
}
