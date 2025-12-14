package com.ecommerce.project.DTO;

public class ProductDTO {
    private Long productId;
    private String productName;
    private String model;
    private String image;
    private String description;
    private Integer quantity;
    private double price;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, String productName, String model, String image, String description, Integer quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.model = model;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
