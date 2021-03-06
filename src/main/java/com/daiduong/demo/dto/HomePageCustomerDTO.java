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
public class HomePageCustomerDTO {
    
    private List<CategoryDTO> categoryList;
    private ProductPagingDTO productPaging;

}
