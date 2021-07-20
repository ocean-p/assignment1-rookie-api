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
public class CategoryDTO {
    private int id;

    @NotNull
    private String name;

    private String description;
    private LocalDate createDate;
    private LocalDate updateDate;
    private boolean isDeleted;

}
