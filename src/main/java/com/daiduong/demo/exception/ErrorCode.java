package com.daiduong.demo.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ErrorCode {
    private final String CART_ERR = "CART_ERR";

    private final String RATING_ERR = "RATING_ERR";

    private final String SEARCH_PRODUCT_ERR = "SEARCH_PRODUCT_ERR";

    private final String LOAD_PRODUCT_ERR = "LOAD_PRODUCT_ERR";

    private final String RESTORE_PRODUCT_ERR = "RESTORE_PRODUCT_ERR";

    private final String DELETE_PRODUCT_ERR = "DELETE_PRODUCT_ERR";

    private final String UPDATE_PRODUCT_ERR = "UPDATE_PRODUCT_ERR";

    private final String ADD_PRODUCT_ERR = "ADD_PRODUCT_ERR";

    private final String SEARCH_CATEGORY_ERR = "SEARCH_CATEGORY_ERR";

    private final String LOAD_CATEGORY_ERR = "LOAD_CATEGORY_ERR";

    private final String RESTORE_CATEGORY_ERR = "RESTORE_CATEGORY_ERR";

    private final String DELETE_CATEGORY_ERR = "DELETE_CATEGORY_ERR";

    private final String UPDATE_CATEGORY_ERR = "UPDATE_CATEGORY_ERR";

    private final String ADD_CATEGORY_ERR = "ADD_CATEGORY_ERR";

    private final String SEARCH_ACCOUNT_ERR = "SEARCH_ACCOUNT_ERR";

    private final String LOAD_ACCOUNT_ERR = "LOAD_ACCOUNT_ERR";

    private final String RESTORE_ACCOUNT_ERR = "RESTORE_ACCOUNT_ERR";

    private final String DELETE_ACCOUNT_ERR = "DELETE_ACCOUNT_ERR";

    private final String ADD_ACCOUNT_ERR = "ADD_ACCOUNT_ERR";

    private final String UPDATE_ACCOUNT_ERR = "UPDATE_ACCOUNT_ERR";

    private final String USERNAME_NOT_CORRECT_FORMAT = "USERNAME_NOT_CORRECT_FORMAT";

    private final String USERNAME_ALREADY_TAKEN = "USERNAME_ALREADY_TAKEN";

    private final String PASSWORD_NOT_CORRECT_FORMAT = "PASSWORD_NOT_CORRECT_FORMAT";
    
    private final String FULLNAME_IS_EMPTY = "FULLNAME_IS_EMPTY";

    private final String PHONE_NOT_CORRECT_FORMAT = "PHONE_NOT_CORRECT_FORMAT";

    private final String ADDRESS_IS_EMPTY = "ADDRESS_IS_EMPTY";

    private final String ROLE_NOT_CORRECT = "ROLE_NOT_CORRECT";

    private final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";

    private final String ACCOUNT_NOT_BELONG_TO_CUSTOMER = "ACCOUNT_NOT_BELONG_TO_CUSTOMER";

    private final String ACCOUNT_IS_DISABLED = "ACCOUNT_IS_DISABLED";

    private final String ACCOUNT_ACTIVE = "ACCOUNT_ACTIVE";

    private final String PAGE_LESS_THAN_ONE = "PAGE_LESS_THAN_ONE";

    private final String SEARCH_VALUE_IS_EMPTY = "SEARCH_VALUE_IS_EMPTY";

    private final String CATEGORY_NOT_FOUND = "CATEGORY_NOT_FOUND";

    private final String CATEGORY_IS_DISABLED = "CATEGORY_IS_DISABLED";

    private final String CATEGORY_ACTIVE = "CATEGORY_ACTIVE";

    private final String NAME_IS_EMPTY = "NAME_IS_EMPTY";

    private final String DESCRIPTION_IS_EMPTY = "DESCRIPTION_IS_EMPTY";

    private final String IMAGEURL_IS_EMPTY = "IMAGEURL_IS_EMPTY";

    private final String PRICE_LESS_THAN_ZERO = "PRICE_LESS_THAN_ZERO";

    private final String QUANTITY_LESS_THAN_ZERO = "QUANTITY_LESS_THAN_ZERO";

    private final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";

    private final String PRODUCT_IS_DISABLED = "PRODUCT_IS_DISABLED";

    private final String PRODUCT_ACTIVE = "PRODUCT_ACTIVE";

    private final String POINT_NOT_CORRECT = "POINT_NOT_CORRECT";

    private final String RATING_ALREADY = "RATING_ALREADY";

    private final String RATING_NOT_FOUND = "RATING_NOT_FOUND";

    private final String CART_NOT_FOUND = "CART_NOT_FOUND";

    private final String NO_ITEM_IN_CART = "NO_ITEM_IN_CART";

    private final String QUANTITY_GREATER_THAN_AVAILABLE = "QUANTITY_GREATER_THAN_AVAILABLE";
}
