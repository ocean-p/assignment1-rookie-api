package com.daiduong.demo.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ErrorCode {
    
    private final String USERNAME_IS_EMPTY = "USERNAME_IS_EMPTY";

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
}
