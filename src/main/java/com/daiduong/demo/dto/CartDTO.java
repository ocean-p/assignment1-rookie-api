package com.daiduong.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDTO {
    
    private ProductDTO products;
    private int quantity;
    private String username;
    
}
