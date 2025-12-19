package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.DTO.PaymentCardDTO;
import com.ecommerce.project.service.Interface.IPaymentCardService;
import com.ecommerce.project.helper.AuthHelper;
import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.errorHandler.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for payment card management
 * Handles CRUD operations for user payment methods
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class PaymentCardController {

    @Autowired
    AuthHelper authHelper;

    @Autowired
    IPaymentCardService IPaymentCardService;

    @Autowired
    UserDAO userDAO;

    /**
     * Add a new payment card for current user
     * Endpoint: POST /api/payment-methods
     * Saves card details to user's account
     */
    @PostMapping("/payment-methods")
    public ResponseEntity<PaymentCardDTO> createPaymentCard(@Valid @RequestBody PaymentCardDTO paymentCardDTO) {
        User user = authHelper.loggedInUser();
        PaymentCardDTO savedCard = IPaymentCardService.createPaymentCard(paymentCardDTO, user);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    /**
     * Get all payment cards in the system
     * Endpoint: GET /api/admin/payment-methods
     * Admin only - returns all user payment methods
     */
    @GetMapping("/admin/payment-methods")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentCardDTO>> getAllPaymentCards() {
        List<PaymentCardDTO> cards = IPaymentCardService.getPaymentCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    /**
     * Get a specific payment card by ID
     * Endpoint: GET /api/payment-methods/{cardId}
     * Returns card details for given ID
     */
    @GetMapping("/payment-methods/{cardId}")
    public ResponseEntity<PaymentCardDTO> getPaymentCardById(@PathVariable Long cardId) {
        PaymentCardDTO card = IPaymentCardService.getPaymentCardById(cardId);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    /**
     * Get all payment cards for current user
     * Endpoint: GET /api/payment-methods
     * Returns logged-in user's saved payment methods
     */
    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentCardDTO>> getUserPaymentCards() {
        User user = authHelper.loggedInUser();
        List<PaymentCardDTO> cards = IPaymentCardService.getUserPaymentCards(user);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    /**
     * Get current user's default payment card
     * Endpoint: GET /api/payment-methods/default
     * Returns the card marked as default for user
     */
    @GetMapping("/payment-methods/default")
    public ResponseEntity<PaymentCardDTO> getUserDefaultCard() {
        User user = authHelper.loggedInUser();
        PaymentCardDTO card = IPaymentCardService.getUserDefaultCard(user);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    /**
     * Get all payment cards for a specific user
     * Endpoint: GET /api/admin/users/{userId}/payment-methods
     * Admin only - view any user's payment methods
     */
    @GetMapping("/admin/users/{userId}/payment-methods")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentCardDTO>> getUserPaymentCardsByUserId(@PathVariable Long userId) {
        List<PaymentCardDTO> cards = IPaymentCardService.getUserPaymentCardsByUserId(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    /**
     * Update an existing payment card
     * Endpoint: PUT /api/payment-methods/{cardId}
     * Updates card details (number, expiry, etc.)
     */
    @PutMapping("/payment-methods/{cardId}")
    public ResponseEntity<PaymentCardDTO> updatePaymentCard(
            @PathVariable Long cardId,
            @RequestBody PaymentCardDTO paymentCardDTO) {
        PaymentCardDTO updated = IPaymentCardService.updatePaymentCard(cardId, paymentCardDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Set a card as default payment method
     * Endpoint: PUT /api/payment-methods/{cardId}/default
     * Marks card as default, unmarks all other cards
     */
    @PutMapping("/payment-methods/{cardId}/default")
    public ResponseEntity<PaymentCardDTO> setDefaultCard(@PathVariable Long cardId) {
        User user = authHelper.loggedInUser();
        PaymentCardDTO updated = IPaymentCardService.setDefaultCard(cardId, user);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Delete a payment card
     * Endpoint: DELETE /api/payment-methods/{cardId}
     * Removes card from user's account
     */
    @DeleteMapping("/payment-methods/{cardId}")
    public ResponseEntity<String> deletePaymentCard(@PathVariable Long cardId) {
        String status = IPaymentCardService.deletePaymentCard(cardId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    /**
     * Admin: Create a new payment card for a specific user
     * Endpoint: POST /api/admin/users/{userId}/payment-methods
     * Admin only - add payment card to any user's account
     */
    @PostMapping("/admin/users/{userId}/payment-methods")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentCardDTO> createPaymentCardForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PaymentCardDTO paymentCardDTO) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        PaymentCardDTO savedCard = IPaymentCardService.createPaymentCard(paymentCardDTO, user);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    /**
     * Admin: Update a payment card for a specific user
     * Endpoint: PUT /api/admin/users/{userId}/payment-methods/{cardId}
     * Admin only - update any user's payment card
     */
    @PutMapping("/admin/users/{userId}/payment-methods/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentCardDTO> updatePaymentCardForUser(
            @PathVariable Long userId,
            @PathVariable Long cardId,
            @RequestBody PaymentCardDTO paymentCardDTO) {
        PaymentCardDTO updated = IPaymentCardService.updatePaymentCard(cardId, paymentCardDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Admin: Delete a payment card for a specific user
     * Endpoint: DELETE /api/admin/users/{userId}/payment-methods/{cardId}
     * Admin only - remove any user's payment card
     */
    @DeleteMapping("/admin/users/{userId}/payment-methods/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePaymentCardForUser(
            @PathVariable Long userId,
            @PathVariable Long cardId) {
        String status = IPaymentCardService.deletePaymentCard(cardId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
