package com.daiduong.demo.composite;

import java.io.Serializable;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.ProductEntity;

public class RatingCompositeKey implements Serializable{
    private ProductEntity product;
    private AccountEntity account;

    public RatingCompositeKey() {
    }

    public RatingCompositeKey(ProductEntity product, AccountEntity account) {
        this.product = product;
        this.account = account;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
