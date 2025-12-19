package com.ecommerce.project.errorHandler;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import com.ecommerce.project.DTO.APIResponse;

/**
 * Global error handler for the entire application.
 * Catches exceptions thrown anywhere in the app and converts them to proper HTTP responses.
 * Returns user-friendly error messages instead of technical stack traces.
 */
@RestControllerAdvice
public class MyGlobalErrorHandler {

    private final MessageSource messageSource;

    public MyGlobalErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles validation errors (e.g., invalid email format, missing required fields).
     * Returns 400 Bad Request with a map of field names to error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> validationErrors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            validationErrors.put(fieldName, message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }

    /**
     * Helper method to build consistent API error responses.
     */
    private ResponseEntity<APIResponse> buildAPIResponse(String message, boolean success, HttpStatus status) {
        APIResponse apiResponse = new APIResponse(message, success);
        return ResponseEntity.status(status).body(apiResponse);
    }

    /**
     * Handles ResourceNotFoundException (when requested data doesn't exist).
     * Returns 404 Not Found with descriptive error message.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> myResourceNotFoundException(ResourceNotFoundException e) {
        return buildAPIResponse(e.getMessage(), false, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles general API errors (business logic errors, constraint violations, etc.).
     * Returns 400 Bad Request with error message.
     */
    @ExceptionHandler(APIErrorHandler.class)
    public ResponseEntity<?> myAPIException(APIErrorHandler e) {
        return buildAPIResponse(e.getMessage(), false, HttpStatus.BAD_REQUEST);
    }
}
