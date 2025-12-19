// Payment entity - stores payment transaction details
package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payments")
public class Payment {

    // Unique ID for each payment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // Order associated with this payment
    @OneToOne(mappedBy = "payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Order order;

    // Payment method used (e.g., CREDIT_CARD, DEBIT_CARD)
    @NotBlank
    @Size(min = 4, message = "Payment method must contain at least 4 characters")
    private String paymentMethod;

    // Payment gateway transaction ID
    private String pgPaymentId;
    // Payment gateway status
    private String pgStatus;
    // Response message from payment gateway
    private String pgResponseMessage;

    // Payment gateway name
    private String pgName;

    // Default constructor
    public Payment() {
    }

    // Constructor with all fields
    public Payment(Long paymentId, Order order, String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }

    public Payment(String paymentMethod, String pgPaymentId, String pgStatus,
                   String pgResponseMessage, String pgName) {
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

    // Get associated order
    public Order getOrder() {
        return order;
    }

    // Set associated order
    public void setOrder(Order order) {
        this.order = order;
    }

    // Get payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Set payment method
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Get payment gateway transaction ID
    public String getPgPaymentId() {
        return pgPaymentId;
    }

    // Set payment gateway transaction ID
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
