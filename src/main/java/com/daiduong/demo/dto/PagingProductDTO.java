package com.daiduong.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagingProductDTO {
    private int currentPage;
    private int totalPages;
    private Long totalItems;
    private List<ProductDTO> productList;
}
