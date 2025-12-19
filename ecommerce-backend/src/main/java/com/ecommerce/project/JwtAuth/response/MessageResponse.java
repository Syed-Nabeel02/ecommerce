package com.ecommerce.project.JwtAuth.response;

/**
 * Simple response DTO for returning messages.
 * Used for success/error messages in authentication operations.
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}