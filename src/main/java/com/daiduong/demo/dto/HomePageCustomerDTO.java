package com.daiduong.demo.dto;

import java.util.List;

public class HomePageCustomerDTO {
    
    private List<CategoryDTO> categoryList;
    private List<ProductDTO> productList;

    public HomePageCustomerDTO() {
    }

    public HomePageCustomerDTO(List<CategoryDTO> categoryList, List<ProductDTO> productList) {
        this.categoryList = categoryList;
        this.productList = productList;
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

}
