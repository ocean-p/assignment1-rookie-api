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
public class AccountDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;
    private LocalDate createDate;
    private LocalDate updateDate;

    @NotBlank
    private String role;
    private boolean isDeleted;
}
