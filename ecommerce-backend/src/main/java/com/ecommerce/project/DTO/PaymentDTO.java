package com.ecommerce.project.DTO;

// PaymentDTO - data transfer object for payment transaction information
public class PaymentDTO {
    // Unique identifier for the payment
    private Long paymentId;
    // Payment method used (e.g., Credit Card, PayPal)
    private String paymentMethod;
    // Payment ID from payment gateway
    private String pgPaymentId;
    // Payment status from gateway (e.g., SUCCESS, FAILED)
    private String pgStatus;
    // Response message from payment gateway
    private String pgResponseMessage;
    // Name of the payment gateway (e.g., Stripe, Razorpay)
    private String pgName;

    // Default constructor
    public PaymentDTO() {
    }

    // Constructor with all fields
    public PaymentDTO(Long paymentId, String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }

    // Get payment ID
    public Long getPaymentId() {
        return paymentId;
    }

    // Set payment ID
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    // Get payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Set payment method
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    // Get payment gateway name
    public String getPgName() {
        return pgName;
    }

    // Set payment gateway name
    public void setPgName(String pgName) {
        this.pgName = pgName;
    }
}
