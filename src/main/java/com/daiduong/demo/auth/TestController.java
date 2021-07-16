package com.daiduong.demo.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

    @GetMapping("/customer")
	@PreAuthorize("hasRole('CUSTOMER')")
	public String userAccess() {
		return "User Content.";
	}

    @GetMapping("/mod")
	@PreAuthorize("hasRole('ADMIN')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
