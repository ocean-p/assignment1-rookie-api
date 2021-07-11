package com.daiduong.demo.dto;

import java.util.List;

public class PagingProductDTO {
    private int currentPage;
    private int totalPages;
    private Long totalItems;
    private List<ProductDTO> productList;
    
    public PagingProductDTO() {
    }

    public PagingProductDTO(int currentPage, int totalPages, Long totalItems, List<ProductDTO> productList) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.productList = productList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

   
}
