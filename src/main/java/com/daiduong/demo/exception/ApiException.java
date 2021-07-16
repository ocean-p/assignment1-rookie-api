package com.daiduong.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiException {
    
    private final String message;
    private final HttpStatus httpsStatus;
    private final LocalDateTime time;

}
