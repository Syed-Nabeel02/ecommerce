package com.ecommerce.project.DTO;

// AddressDto - data transfer object for delivery address information
public class AddressDto {
    // Unique identifier for the address
    private Long addressId;
    // Street name and number
    private String street;
    // Building or apartment name
    private String buildingName;
    // City name
    private String city;
    // State or province
    private String state;
    // Country name
    private String country;
    // Postal/ZIP code
    private String pincode;

    // Default constructor
    public AddressDto() {
    }

    // Constructor with all fields
    public AddressDto(Long addressId, String street, String buildingName, String city, String state, String country, String pincode) {
        this.addressId = addressId;
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

    // Get street name
    public String getStreet() {
        return street;
    }

    // Set street name
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

    // Get pincode
    public String getPincode() {
        return pincode;
    }

    // Set pincode
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
