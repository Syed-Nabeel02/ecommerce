package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.DTO.AddressDto;
import com.ecommerce.project.service.Interface.IAddrService;
import com.ecommerce.project.helper.AuthHelper;
import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.errorHandler.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for address management
 * Handles CRUD operations for user shipping/billing addresses
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
public class AddrController {

    private final AuthHelper authHelper;
    private final IAddrService IAddrService;
    private final UserDAO userDAO;

    public AddrController(AuthHelper authHelper, IAddrService IAddrService, UserDAO userDAO) {
        this.authHelper = authHelper;
        this.IAddrService = IAddrService;
        this.userDAO = userDAO;
    }

    /**
     * Get all addresses in the system
     * Endpoint: GET /api/admin/addresses
     * Admin only - returns all user addresses
     */
    @GetMapping("/admin/addresses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AddressDto>> getAddr() {
        List<AddressDto> addressList = IAddrService.getAddr();
        return ResponseEntity.ok(addressList);
    }

    /**
     * Get a specific address by ID
     * Endpoint: GET /api/addresses/{addressId}
     * Returns address details for given ID
     */
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDto> getAddrById(@PathVariable Long addressId) {
        AddressDto addressDTO = IAddrService.getAddrById(addressId);
        return ResponseEntity.ok(addressDTO);
    }

    /**
     * Get all addresses for a specific user
     * Endpoint: GET /api/admin/users/{userId}/addresses
     * Admin only - view any user's addresses
     */
    @GetMapping("/admin/users/{userId}/addresses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AddressDto>> getAddrByUserId(@PathVariable Long userId) {
        List<AddressDto> addressList = IAddrService.getAddrByUserId(userId);
        return ResponseEntity.ok(addressList);
    }

    /**
     * Create a new address for current user
     * Endpoint: POST /api/addresses
     * Adds address to logged-in user's account
     */
    @PostMapping("/addresses")
    public ResponseEntity<AddressDto> newAddr(@Valid @RequestBody AddressDto addressDTO) {
        User user = authHelper.loggedInUser();
        AddressDto savedAddressDto = IAddrService.newAddr(addressDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddressDto);
    }

    /**
     * Get all addresses for current user
     * Endpoint: GET /api/addresses
     * Returns logged-in user's saved addresses
     */
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDto>> getUserAddr() {
        User user = authHelper.loggedInUser();
        List<AddressDto> addressList = IAddrService.getUserAddr(user);
        return ResponseEntity.ok(addressList);
    }

    /**
     * Update an existing address
     * Endpoint: PUT /api/addresses/{addressId}
     * Updates address details (street, city, state, etc.)
     */
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDto> updateAddr(@PathVariable Long addressId,
                                                 @RequestBody AddressDto addressDTO) {
        AddressDto updatedAddress = IAddrService.updateAddr(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    /**
     * Delete an address
     * Endpoint: DELETE /api/addresses/{addressId}
     * Removes address from user's account
     */
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddr(@PathVariable Long addressId) {
        String status = IAddrService.delAddr(addressId);
        return ResponseEntity.ok(status);
    }

    /**
     * Admin: Create a new address for a specific user
     * Endpoint: POST /api/admin/users/{userId}/addresses
     * Admin only - add address to any user's account
     */
    @PostMapping("/admin/users/{userId}/addresses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddressDto> createAddrForUser(
            @PathVariable Long userId,
            @Valid @RequestBody AddressDto addressDTO) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        AddressDto savedAddressDto = IAddrService.newAddr(addressDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddressDto);
    }

    /**
     * Admin: Update an address for a specific user
     * Endpoint: PUT /api/admin/users/{userId}/addresses/{addressId}
     * Admin only - update any user's address
     */
    @PutMapping("/admin/users/{userId}/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddressDto> updateAddrForUser(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody AddressDto addressDTO) {
        AddressDto updatedAddress = IAddrService.updateAddr(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    /**
     * Admin: Delete an address for a specific user
     * Endpoint: DELETE /api/admin/users/{userId}/addresses/{addressId}
     * Admin only - remove any user's address
     */
    @DeleteMapping("/admin/users/{userId}/addresses/{addressId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAddrForUser(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        String status = IAddrService.delAddr(addressId);
        return ResponseEntity.ok(status);
    }
}