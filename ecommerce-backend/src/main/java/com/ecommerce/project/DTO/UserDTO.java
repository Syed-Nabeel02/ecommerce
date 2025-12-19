package com.ecommerce.project.DTO;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.project.model.Role;

// UserDTO - data transfer object for user information sent to frontend
public class UserDTO {

	// Unique identifier for the user
	private Long userId;
	// User's login name
	private String username;
	// User's email address
	private String email;
	// User's password (encrypted)
	private String password;
	// User's assigned roles (e.g., ADMIN, USER)
	private Set<Role> roles = new HashSet<>();
	// User's delivery address
	private AddressDto address;
	// User's shopping cart
	private CartDto cart;

	// Default constructor
	public UserDTO() {
	}

	// Constructor with all fields
	public UserDTO(Long userId, String username, String email, String password, Set<Role> roles, AddressDto address, CartDto cart) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.address = address;
		this.cart = cart;
	}

	// Get user ID
	public Long getUserId() {
		return userId;
	}

	// Set user ID
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	// Get username
	public String getUsername() {
		return username;
	}

	// Set username
	public void setUsername(String username) {
		this.username = username;
	}

	// Get email
	public String getEmail() {
		return email;
	}

	// Set email
	public void setEmail(String email) {
		this.email = email;
	}

	// Get password
	public String getPassword() {
		return password;
	}

	// Set password
	public void setPassword(String password) {
		this.password = password;
	}

	// Get user roles
	public Set<Role> getRoles() {
		return roles;
	}

	// Set user roles
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// Get user address
	public AddressDto getAddress() {
		return address;
	}

	// Set user address
	public void setAddress(AddressDto address) {
		this.address = address;
	}

	// Get user cart
	public CartDto getCart() {
		return cart;
	}

	// Set user cart
	public void setCart(CartDto cart) {
		this.cart = cart;
	}
}
