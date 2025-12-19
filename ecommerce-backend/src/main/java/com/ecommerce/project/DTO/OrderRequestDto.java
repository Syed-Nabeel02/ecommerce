package com.ecommerce.project.DTO;

// OrderRequestDto - data transfer object for order creation request from frontend
public class OrderRequestDto {
    // ID of the delivery address
    private Long addressId;
    // Optional: ID of saved card to use for payment
    private Long cardId;
    // Payment method (e.g., Credit Card, PayPal)
    private String paymentMethod;
    // Payment gateway name (e.g., Stripe, Razorpay)
    private String pgName;
    // Payment ID from payment gateway
    private String pgPaymentId;
    // Payment status from gateway (e.g., SUCCESS, FAILED)
    private String pgStatus;
    // Response message from payment gateway
    private String pgResponseMessage;

    // Default constructor
    public OrderRequestDto() {
    }

    // Constructor with all fields
    public OrderRequestDto(Long addressId, Long cardId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
        this.addressId = addressId;
        this.cardId = cardId;
        this.paymentMethod = paymentMethod;
        this.pgName = pgName;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
    }

    // Get address ID
    public Long getAddressId() {
        return addressId;
    }

    // Set address ID
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    // Get saved card ID
    public Long getCardId() {
        return cardId;
    }

    // Set saved card ID
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    // Get payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Set payment method
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Get payment gateway name
    public String getPgName() {
        return pgName;
    }

    // Set payment gateway name
    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    // Get payment gateway payment ID
    public String getPgPaymentId() {
        return pgPaymentId;
    }

    // Set payment gateway payment ID
    public void setPgPaymentId(String pgPaymentId) {
        this.pgPaymentId = pgPaymentId;
    }

    // Get payment gateway status
    public String getPgStatus() {
        return pgStatus;
    }

    // Set payment gateway status
    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }

    // Get payment gateway response message
    public String getPgResponseMessage() {
        return pgResponseMessage;
    }

    // Set payment gateway response message
    public void setPgResponseMessage(String pgResponseMessage) {
        this.pgResponseMessage = pgResponseMessage;
    }
}
