package com.daiduong.demo.auth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.payload.request.LoginRequest;
import com.daiduong.demo.payload.request.SignupRequest;
import com.daiduong.demo.payload.response.JwtResponse;
import com.daiduong.demo.payload.response.MessageResponse;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.security.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AccountEntity userDetails = (AccountEntity) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getUsername(), userDetails.getFullName(),
                                userDetails.getPhone(), userDetails.getAddress() , roles.get(0)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        if(signUpRequest.getUsername() == null 
            || signUpRequest.getUsername().trim().length() == 0
            || !signUpRequest.getUsername().trim().matches("^[0-9A-Za-z]+$")) 
        {
            return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Username is not correct format!"));
        }

        if(signUpRequest.getPassword() == null 
            || !signUpRequest.getPassword().trim().matches("^[0-9A-Za-z]+$")
            || signUpRequest.getPassword().trim().length() < 6
            || signUpRequest.getPassword().trim().length() > 20) 
        {
            return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Password is not correct format!"));
        }

        if(signUpRequest.getFullName() == null || signUpRequest.getFullName().trim().length() == 0) {
            return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Fullname is null or empty!"));
        }

        if(signUpRequest.getPhone() == null 
            || signUpRequest.getPhone().trim().length() < 10
            || signUpRequest.getPhone().trim().length() > 11
            || !signUpRequest.getPhone().trim().matches("^[0-9]+$")) 
        {
            return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Phone isn't correct format"));
        }

        if(signUpRequest.getAddress() == null || signUpRequest.getAddress().trim().length() == 0) {
            return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Address is null or empty!"));
        }

        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Username is already taken!"));
        }

        LocalDate currentDate = LocalDate.now();
        // Create new user's account
        AccountEntity account = new AccountEntity(signUpRequest.getUsername(),
                                    encoder.encode(signUpRequest.getPassword()),
                                    signUpRequest.getFullName(), signUpRequest.getPhone(),
                                    signUpRequest.getAddress(), currentDate, currentDate,
                                    "ROLE_CUSTOMER", false);
        accountRepository.save(account);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
