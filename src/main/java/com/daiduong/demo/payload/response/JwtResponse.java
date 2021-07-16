package com.daiduong.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
	private String type = "Bearer";
	private String username;
    private String fullName;
    private String phone;
    private String address;
	private String roles;

    public JwtResponse(String token, String username, String fullName, String phone, String address, String roles) {
        this.token = token;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
    }
}
