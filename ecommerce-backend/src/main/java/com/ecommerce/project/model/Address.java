// Address entity - stores shipping/billing addresses for users
package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address {
    // Unique ID for each address
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    // User who owns this address
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Street address (min 5 characters)
    @NotBlank
    @Size(min = 5, message = "must be atleast 5 characters")
    private String street;

    // Building/apartment name (min 5 characters)
    @NotBlank
    @Size(min = 5, message = "Building name must be atleast 5 characters")
    private String buildingName;

    // City name (min 4 characters)
    @NotBlank
    @Size(min = 4, message = "City name must be atleast 4 characters")
    private String city;

    // State/province name (min 2 characters)
    @NotBlank
    @Size(min = 2, message = "State name must be atleast 2 characters")
    private String state;

    // Country name (min 2 characters)
    @NotBlank
    @Size(min = 2, message = "Country name must be atleast 2 characters")
    private String country;

    // Postal/ZIP code (min 5 characters)
    @NotBlank
    @Size(min = 5, message = "Postal Code must be atleast 5 characters")
    private String pincode;

    // Default constructor
    public Address() {
    }

    // Constructor with all fields including ID and user
    public Address(Long addressId, String street, String buildingName, String city, String state, String country, String pincode, User user) {
        this.addressId = addressId;
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.user = user;
    }

    // Constructor with address fields only (no ID or user)
    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    // Get address ID
    public Long getAddressId() {
        return addressId;
    }

    // Set address ID
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    // Get street address
    public String getStreet() {
        return street;
    }

    // Set street address
    public void setStreet(String street) {
        this.street = street;
    }

    // Get building name
    public String getBuildingName() {
        return buildingName;
    }

    // Set building name
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    // Get city
    public String getCity() {
        return city;
    }

    // Set city
    public void setCity(String city) {
        this.city = city;
    }

    // Get state
    public String getState() {
        return state;
    }

    // Set state
    public void setState(String state) {
        this.state = state;
    }

    // Get country
    public String getCountry() {
        return country;
    }

    // Set country
    public void setCountry(String country) {
        this.country = country;
    }

    // Get postal code
    public String getPincode() {
        return pincode;
    }

    // Set postal code
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    // Get user who owns this address
    public User getUser() {
        return user;
    }

    // Set user who owns this address
    public void setUser(User user) {
        this.user = user;
    }
}
