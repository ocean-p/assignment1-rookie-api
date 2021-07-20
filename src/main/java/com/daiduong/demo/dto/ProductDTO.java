package com.daiduong.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

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

    @NotNull
    private String name;

    @NotNull
    private float price;

    @NotNull
    private String image;

    @NotNull
    private int quantity;
    private String description;
    private int averageRate;
    private LocalDate createDate;
    private LocalDate updateDate;

    @NotNull
    private int categoryId;
    private boolean isDeleted;
}
