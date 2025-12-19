package com.ecommerce.project.controller;

import com.ecommerce.project.config.Constants;
import com.ecommerce.project.DTO.AuthenticationResult;
import com.ecommerce.project.JwtAuth.request.LoginRequest;
import com.ecommerce.project.JwtAuth.request.SignupRequest;
import com.ecommerce.project.JwtAuth.request.UpdateUsernameRequest;
import com.ecommerce.project.JwtAuth.response.MessageResponse;
import com.ecommerce.project.service.Interface.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user authentication and profile management
 * Handles user registration, login, logout, and profile operations
 * Base URL: /api/auth
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final IAuthenticationService IAuthenticationService;

    public AuthenticationController(IAuthenticationService IAuthenticationService) {
        this.IAuthenticationService = IAuthenticationService;
    }

    /**
     * Register a new user account
     * Endpoint: POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return IAuthenticationService.register(signUpRequest);
    }

    /**
     * Login user and create session
     * Endpoint: POST /api/auth/login
     * Returns JWT cookie and user details
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid login request"));
        }

        AuthenticationResult result = IAuthenticationService.login(loginRequest);
        if (result == null || result.getJwtCookie() == null) {
            return ResponseEntity.status(401).body(new MessageResponse("Authentication failed"));
        }

        String cookie = result.getJwtCookie().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .body(result.getResponse());
    }

    /**
     * Get current logged-in user's username
     * Endpoint: GET /api/auth/profile/username
     */
    @GetMapping("/profile/username")
    public String currentUserName(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return "";
        }
        return authentication.getName();
    }

    /**
     * Get current logged-in user's full profile details
     * Endpoint: GET /api/auth/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        return ResponseEntity.ok().body(IAuthenticationService.getCurrentUserDetails(authentication));
    }

    /**
     * Logout user and clear session
     * Endpoint: POST /api/auth/logout
     * Clears JWT cookie
     */
    @PostMapping("/logout")
    public ResponseEntity<?> signoutUser() {
        ResponseCookie cookie = IAuthenticationService.logoutUser();
        if (cookie == null) {
            return ResponseEntity.ok().body(new MessageResponse("No active session"));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    /**
     * Get all customers (users with ROLE_USER)
     * Endpoint: GET /api/auth/admin/users
     * Admin only - requires ADMIN role
     * Supports pagination
     */
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(name = "pageNumber", defaultValue = Constants.page_num, required = false) Integer pageNumber) {

        int safePageNumber = (pageNumber == null || pageNumber < 0) ? 0 : pageNumber;

        int pageSize;
        try {
            pageSize = Integer.parseInt(Constants.page_size);
            if (pageSize <= 0) pageSize = 10;
        } catch (NumberFormatException ex) {
            pageSize = 10;
        }

        Sort sortByAndOrder = Sort.by(Constants.users_sort_by).descending();
        Pageable pageDetails = PageRequest.of(safePageNumber, pageSize, sortByAndOrder);

        return ResponseEntity.ok(IAuthenticationService.getAllCustomers(pageDetails));
    }

    /**
     * Update current user's username
     * Endpoint: PUT /api/auth/profile/username
     * Validates that new username is not already taken
     */
    @PutMapping("/profile/username")
    public ResponseEntity<?> updateUsername(
            @Valid @RequestBody UpdateUsernameRequest request,
            Authentication authentication) {
        return IAuthenticationService.updateUsername(request, authentication);
    }

}