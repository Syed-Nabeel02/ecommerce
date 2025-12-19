package com.ecommerce.project.JwtAuth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles authentication errors (unauthorized access attempts).
 * Triggered when a user tries to access a protected endpoint without valid authentication.
 * Returns a JSON error response with 401 Unauthorized status.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    private static final int UNAUTHORIZED_STATUS = HttpServletResponse.SC_UNAUTHORIZED;
    private static final String ERROR_LABEL = "error";
    private static final String MESSAGE_LABEL = "message";
    private static final String STATUS_LABEL = "status";
    private static final String PATH_LABEL = "path";

    private final MessageSource messageSource;

    public AuthEntryPointJwt(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Called when an unauthenticated user tries to access a protected endpoint.
     * Logs the error and sends a 401 Unauthorized JSON response.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logUnauthorizedAttempt(authException);
        configureResponseHeaders(response);

        Map<String, Object> errorResponseBody = buildErrorResponse(request, authException);
        writeErrorResponseToStream(response, errorResponseBody);
    }

    private void logUnauthorizedAttempt(AuthenticationException authException) {
        String logMessage = messageSource.getMessage(
                "auth.error.unauthorized",
                new Object[]{authException.getMessage()},
                "Unauthorized error: " + authException.getMessage(),
                LocaleContextHolder.getLocale()
        );
        logger.error(logMessage);
    }

    private void configureResponseHeaders(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED_STATUS);
    }

    private Map<String, Object> buildErrorResponse(HttpServletRequest request, AuthenticationException authException) {
        Map<String, Object> errorBody = new HashMap<>();
        String errorMessage = messageSource.getMessage(
                "auth.error.message",
                null,
                "Unauthorized",
                LocaleContextHolder.getLocale()
        );

        errorBody.put(STATUS_LABEL, UNAUTHORIZED_STATUS);
        errorBody.put(ERROR_LABEL, errorMessage);
        errorBody.put(MESSAGE_LABEL, authException.getMessage());
        errorBody.put(PATH_LABEL, request.getServletPath());
        return errorBody;
    }

    private void writeErrorResponseToStream(HttpServletResponse response, Map<String, Object> errorResponseBody)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), errorResponseBody);
    }
}
