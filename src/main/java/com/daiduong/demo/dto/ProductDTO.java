package com.daiduong.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    
    private int id;
    private String name;
    private float price;
    private String image;
    private int quantity;
    private String description;
    private int averageRate;
    private LocalDate createDate;
    private LocalDate updateDate;
    private int categoryId;
    private boolean isDeleted;
    private String image2;
    private String image3;
    private String image4;
}
