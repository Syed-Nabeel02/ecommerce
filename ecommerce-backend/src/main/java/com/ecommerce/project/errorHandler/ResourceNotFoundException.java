package com.ecommerce.project.errorHandler;

/**
 * Custom exception thrown when a requested resource is not found.
 * Example: "Product not found with id: 123" or "User not found with email: test@example.com"
 * Provides detailed information about what resource was missing and how it was searched.
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String resourceName;
    private final String field;
    private final String fieldName;
    private final Long fieldId;

    public ResourceNotFoundException() {
        this("Resource", "identifier", null, null);
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        this(resourceName, field, fieldName, null);
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        this(resourceName, field, null, fieldId);
    }

    private ResourceNotFoundException(String resourceName, String field, String fieldName, Long fieldId) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldName != null ? fieldName : String.valueOf(fieldId)));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    /**
     * Factory method for creating exception with a string field value.
     * Example: forName("User", "email", "test@example.com")
     */
    public static ResourceNotFoundException forName(String resourceName, String field, String value) {
        return new ResourceNotFoundException(resourceName, field, value);
    }

    /**
     * Factory method for creating exception with a numeric ID.
     * Example: forId("Product", "productId", 123L)
     */
    public static ResourceNotFoundException forId(String resourceName, String field, Long id) {
        return new ResourceNotFoundException(resourceName, field, id);
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldId() {
        return fieldId;
    }
}
