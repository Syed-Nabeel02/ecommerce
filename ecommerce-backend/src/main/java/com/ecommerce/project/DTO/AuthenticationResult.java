package com.ecommerce.project.DTO;

import com.ecommerce.project.JwtAuth.response.UserInfoResponse;
import org.springframework.http.ResponseCookie;

// AuthenticationResult - data transfer object for authentication response (login/register)
public class AuthenticationResult {
    // User information after successful authentication
    private final UserInfoResponse response;
    // JWT token stored as HTTP cookie
    private final ResponseCookie jwtCookie;

    // Constructor with user info and JWT cookie
    public AuthenticationResult(UserInfoResponse response, ResponseCookie jwtCookie) {
        this.response = response;
        this.jwtCookie = jwtCookie;
    }

    // Get user info response
    public UserInfoResponse getResponse() {
        return response;
    }

    // Get JWT cookie
    public ResponseCookie getJwtCookie() {
        return jwtCookie;
    }
}
