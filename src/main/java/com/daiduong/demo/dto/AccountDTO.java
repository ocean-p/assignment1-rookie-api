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
public class AccountDTO {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String fullName;

    @NotNull
    private String phone;

    @NotNull
    private String address;
    private LocalDate createDate;
    private LocalDate updateDate;

    @NotNull
    private String role;
    private boolean isDeleted;
}
