// PaymentCard entity - stores saved credit/debit card information
package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "payment_cards")
public class PaymentCard {

    // Unique ID for each payment card
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    // Card number (13-19 digits)
    @NotBlank
    @Size(min = 13, max = 19, message = " between 13 and 19 digits")
    @Pattern(regexp = "^[0-9]+$", message = "must contain only digits")
    private String cardNumber;

    // Cardholder's name (min 2 characters)
    @NotBlank
    @Size(min = 2, message = " at least 2 characters")
    private String cardholderName;

    // Expiry month (1-12)
    @NotNull
    @Min(value = 1, message = "must be between 1 and 12")
    @Max(value = 12, message = "must be between 1 and 12")
    private Integer expiryMonth;

    // Expiry year (must be 2024 or later)
    @NotNull
    @Min(value = 2024, message = "Invalid expiry year")
    private Integer expiryYear;

    // Card security code (3-4 digits)
    @NotBlank
    @Size(min = 3, max = 4, message = "must be 3 or 4 digits")
    @Pattern(regexp = "^[0-9]+$", message = "must contain only digits")
    private String cvv;

    // Whether this is the user's default payment method
    private Boolean isDefault = false;

    // User who owns this card
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Default constructor
    public PaymentCard() {
    }

    // Constructor with all fields
    public PaymentCard(Long cardId, String cardNumber, String cardholderName, Integer expiryMonth, Integer expiryYear, String cvv, Boolean isDefault, User user) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.isDefault = isDefault;
        this.user = user;
    }

    // Constructor without ID for creation
    public PaymentCard(String cardNumber, String cardholderName, Integer expiryMonth,
                      Integer expiryYear, String cvv, Boolean isDefault) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.isDefault = isDefault;
    }

    // Get card ID
    public Long getCardId() {
        return cardId;
    }

    // Set card ID
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    // Get card number
    public String getCardNumber() {
        return cardNumber;
    }

    // Set card number
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    // Get cardholder name
    public String getCardholderName() {
        return cardholderName;
    }

    // Set cardholder name
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    // Get expiry month
    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    // Set expiry month
    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    // Get expiry year
    public Integer getExpiryYear() {
        return expiryYear;
    }

    // Set expiry year
    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    // Get CVV
    public String getCvv() {
        return cvv;
    }

    // Set CVV
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    // Check if this is default card
    public Boolean getIsDefault() {
        return isDefault;
    }

    // Set as default card
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    // Get user who owns card
    public User getUser() {
        return user;
    }

    // Set user who owns card
    public void setUser(User user) {
        this.user = user;
    }
}
