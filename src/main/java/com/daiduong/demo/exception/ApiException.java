package com.daiduong.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiException {
    
    private final String message;
    private final HttpStatus httpsStatus;
    private final LocalDateTime time;

    public ApiException(String message, HttpStatus httpsStatus, LocalDateTime time) {
        this.message = message;
        this.httpsStatus = httpsStatus;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpsStatus() {
        return httpsStatus;
    }

    public LocalDateTime getTime() {
        return time;
    }
    
}
