package com.daiduong.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private String role;
}
