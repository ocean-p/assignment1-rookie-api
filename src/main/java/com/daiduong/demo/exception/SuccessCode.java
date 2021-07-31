package com.daiduong.demo.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class SuccessCode {
    private final String UPDATE_QUANTITY_SUCCESS = "UPDATE_QUANTITY_SUCCESS";

    private final String DELETE_ITEM_SUCCESS = "DELETE_ITEM_SUCCESS";

    private final String ADD_TO_CART_SUCCESS = "ADD_TO_CART_SUCCESS";

    private final String LOAD_CART_SUCCESS = "LOAD_CART_SUCCESS";

    private final String UPDATE_RATING_SUCCESS = "UPDATE_RATING_SUCCESS";

    private final String ADD_RATING_SUCCESS = "ADD_RATING_SUCCESS";

    private final String LOAD_RATING_SUCCESS = "LOAD_RATING_SUCCESS";

    private final String ADD_PRODUCT_SUCCESS = "ADD_PRODUCT_SUCCESS";

    private final String UPDATE_PRODUCT_SUCCESS = "UPDATE_PRODUCT_SUCCESS";

    private final String DELETE_PRODUCT_SUCCESS = "DELETE_PRODUCT_SUCCESS";

    private final String RESTORE_PRODUCT_SUCCESS = "RESTORE_PRODUCT_SUCCESS";

    private final String LOAD_PRODUCT_SUCCESS = "LOAD_PRODUCT_SUCCESS";

    private final String ADD_CATEGORY_SUCCESS = "ADD_CATEGORY_SUCCESS";

    private final String UPDATE_CATEGORY_SUCCESS = "UPDATE_CATEGORY_SUCCESS";

    private final String DELETE_CATEGORY_SUCCESS = "DELETE_CATEGORY_SUCCESS";

    private final String RESTORE_CATEGORY_SUCCESS = "RESTORE_CATEGORY_SUCCESS";

    private final String LOAD_CATEGORY_SUCCESS = "LOAD_CATEGORY_SUCCESS";

    private final String ADD_ACCOUNT_SUCCESS = "ADD_ACCOUNT_SUCCESS";

    private final String UPDATE_ACCOUNT_SUCCESS = "UPDATE_ACCOUNT_SUCCESS";

    private final String DELETE_ACCOUNT_SUCCESS = "DELETE_ACCOUNT_SUCCESS";

    private final String RESTORE_ACCOUNT_SUCCESS = "RESTORE_ACCOUNT_SUCCESS";

    private final String LOAD_ACCOUNT_SUCCESS = "LOAD_ACCOUNT_SUCCESS";

}
