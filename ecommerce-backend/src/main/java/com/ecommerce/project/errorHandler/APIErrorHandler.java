package com.ecommerce.project.errorHandler;

/**
 * Custom exception for general API errors.
 * Used for business logic errors like:
 * - "Product already exists in cart"
 * - "Insufficient stock available"
 * - "Cannot delete category with existing products"
 */
public class APIErrorHandler extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public APIErrorHandler() {
        super();
    }

    public APIErrorHandler(String message) {
        super(message);
    }

    /**
     * Factory method to create exception with a message.
     * Example: APIErrorHandler.of("Product already in cart")
     */
    public static APIErrorHandler of(String message) {
        return new APIErrorHandler(message);
    }

    /**
     * Throws exception only if condition is true.
     * Example: throwIf(cart.isEmpty(), "Cart is empty")
     */
    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new APIErrorHandler(message);
        }
    }
}
