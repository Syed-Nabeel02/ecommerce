package com.ecommerce.project.DTO;

// PaymentCardDTO - data transfer object for saved payment card information
public class PaymentCardDTO {
    // Unique identifier for the card
    private Long cardId;
    // Card number (usually masked for security)
    private String cardNumber;
    // Name on the card
    private String cardholderName;
    // Expiry month (1-12)
    private Integer expiryMonth;
    // Expiry year
    private Integer expiryYear;
    // Card verification value (CVV)
    private String cvv;
    // Whether this is the user's default card
    private Boolean isDefault;

    // Default constructor
    public PaymentCardDTO() {
    }

    // Constructor with all fields
    public PaymentCardDTO(Long cardId, String cardNumber, String cardholderName, Integer expiryMonth, Integer expiryYear, String cvv, Boolean isDefault) {
        this.cardId = cardId;
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

    // Check if this is the default card
    public Boolean getIsDefault() {
        return isDefault;
    }

    // Set default card status
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
