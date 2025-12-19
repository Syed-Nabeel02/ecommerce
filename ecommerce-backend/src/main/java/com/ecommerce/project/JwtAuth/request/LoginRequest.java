package com.ecommerce.project.JwtAuth.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for user login.
 * Contains username and password from login form.
 * Validated to ensure both fields are provided.
 */
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
