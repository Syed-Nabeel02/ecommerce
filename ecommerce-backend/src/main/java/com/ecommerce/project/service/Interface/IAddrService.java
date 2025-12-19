package com.ecommerce.project.service.Interface;

import com.ecommerce.project.model.User;
import com.ecommerce.project.DTO.AddressDto;

import java.util.List;

/**
 * Service interface for address operations
 * Handles user shipping/billing address CRUD operations
 */
public interface IAddrService {
    // Create a new address for a user
    AddressDto newAddr(AddressDto addressDTO, User user);

    // Get all addresses in system (admin function)
    List<AddressDto> getAddr();

    // Get a specific address by ID
    AddressDto getAddrById(Long addressId);

    // Get all addresses for a user
    List<AddressDto> getUserAddr(User user);

    // Get all addresses for a user by user ID
    List<AddressDto> getAddrByUserId(Long userId);

    // Update an existing address
    AddressDto updateAddr(Long addressId, AddressDto addressDTO);

    // Delete an address
    String delAddr(Long addressId);
}
