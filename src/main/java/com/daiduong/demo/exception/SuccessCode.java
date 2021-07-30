package com.daiduong.demo.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class SuccessCode {
    private final String ADD_ACCOUNT_SUCCESS = "ADD_ACCOUNT_SUCCESS";

    private final String UPDATE_ACCOUNT_SUCCESS = "UPDATE_ACCOUNT_SUCCESS";

    private final String DELETE_ACCOUNT_SUCCESS = "DELETE_ACCOUNT_SUCCESS";

    private final String RESTORE_ACCOUNT_SUCCESS = "RESTORE_ACCOUNT_SUCCESS";

    private final String LOAD_ACCOUNT_SUCCESS = "LOAD_ACCOUNT_SUCCESS";

}
