package com.ecommerce.project.service.Interface;

import com.ecommerce.project.DTO.AuthenticationResult;
import com.ecommerce.project.DTO.UserResponse;
import com.ecommerce.project.JwtAuth.request.LoginRequest;
import com.ecommerce.project.JwtAuth.request.SignupRequest;
import com.ecommerce.project.JwtAuth.request.UpdateUsernameRequest;
import com.ecommerce.project.JwtAuth.response.MessageResponse;
import com.ecommerce.project.JwtAuth.response.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

/**
 * Service interface for authentication operations
 * Handles user registration, login, logout, and profile management
 */
public interface IAuthenticationService {

    // Authenticate user and create JWT session
    AuthenticationResult login(LoginRequest loginRequest);

    // Register a new user account
    ResponseEntity<MessageResponse> register(SignupRequest signUpRequest);

    // Get current logged-in user's profile details
    UserInfoResponse getCurrentUserDetails(Authentication authentication);

    // Logout user and clear JWT cookie
    ResponseCookie logoutUser();

    // Get all customers (users with ROLE_USER) with pagination
    UserResponse getAllCustomers(Pageable pageable);

    // Update current user's username
    ResponseEntity<MessageResponse> updateUsername(UpdateUsernameRequest request, Authentication authentication);
}
