package com.daiduong.demo.dto;

public class CartDTO {
    
    private ProductDTO products;
    private int quantity;
    private String username;
    
    public CartDTO() {
    }

    public CartDTO(ProductDTO products, int quantity, String username) {
        this.products = products;
        this.quantity = quantity;
        this.username = username;
    }

    public ProductDTO getProducts() {
        return products;
    }

    public void setProducts(ProductDTO products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
