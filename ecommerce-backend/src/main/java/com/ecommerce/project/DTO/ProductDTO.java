package com.ecommerce.project.DTO;

// ProductDTO - data transfer object for product information
public class ProductDTO {
    // Unique identifier for the product
    private Long productId;
    // Name of the product
    private String productName;
    // Product model number or variant
    private String model;
    // URL to product image
    private String image;
    // Product description
    private String description;
    // Available stock quantity
    private Integer quantity;
    // Product price
    private double price;

    // Default constructor
    public ProductDTO() {
    }

    // Constructor with all fields
    public ProductDTO(Long productId, String productName, String model, String image, String description, Integer quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.model = model;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Get product ID
    public Long getProductId() {
        return productId;
    }

    // Set product ID
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // Get product name
    public String getProductName() {
        return productName;
    }

    // Set product name
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Get model number
    public String getModel() {
        return model;
    }

    // Set model number
    public void setModel(String model) {
        this.model = model;
    }

    // Get image URL
    public String getImage() {
        return image;
    }

    // Set image URL
    public void setImage(String image) {
        this.image = image;
    }

    // Get description
    public String getDescription() {
        return description;
    }

    // Set description
    public void setDescription(String description) {
        this.description = description;
    }

    // Get quantity in stock
    public Integer getQuantity() {
        return quantity;
    }

    // Set quantity in stock
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Get price
    public double getPrice() {
        return price;
    }

    // Set price
    public void setPrice(double price) {
        this.price = price;
    }
}
