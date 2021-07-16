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
public class AccountDTO {
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String role;
    private boolean isDeleted;
}
