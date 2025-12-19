package com.ecommerce.project.service.Interface;

import com.ecommerce.project.model.User;
import com.ecommerce.project.DTO.PaymentCardDTO;

import java.util.List;

/**
 * Service interface for payment card operations
 * Handles user payment method CRUD and default card management
 */
public interface IPaymentCardService {
    // Create a new payment card for a user
    PaymentCardDTO createPaymentCard(PaymentCardDTO paymentCardDTO, User user);

    // Get all payment cards in system (admin function)
    List<PaymentCardDTO> getPaymentCards();

    // Get a specific payment card by ID
    PaymentCardDTO getPaymentCardById(Long cardId);

    // Get all payment cards for a user
    List<PaymentCardDTO> getUserPaymentCards(User user);

    // Get all payment cards for a user by user ID
    List<PaymentCardDTO> getUserPaymentCardsByUserId(Long userId);

    // Update an existing payment card
    PaymentCardDTO updatePaymentCard(Long cardId, PaymentCardDTO paymentCardDTO);

    // Delete a payment card
    String deletePaymentCard(Long cardId);

    // Set a card as default payment method (unmarks all other cards)
    PaymentCardDTO setDefaultCard(Long cardId, User user);

    // Get user's default payment card
    PaymentCardDTO getUserDefaultCard(User user);
}
