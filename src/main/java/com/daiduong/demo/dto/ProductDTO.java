package com.daiduong.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

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

    @NotBlank
    private String name;

    @NotBlank
    private float price;

    @NotBlank
    private String image;

    @NotBlank
    private int quantity;
    private String description;
    private int averageRate;
    private LocalDate createDate;
    private LocalDate updateDate;

    @NotBlank
    private int categoryId;
    private boolean isDeleted;
}
