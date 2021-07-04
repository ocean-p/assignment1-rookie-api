package com.daiduong.demo.composite;

import java.io.Serializable;
import java.util.Objects;

public class RatingCompositeKey implements Serializable{
    private int productId;
    private String username;

    public RatingCompositeKey() {
    }

    public RatingCompositeKey(int productId, String username) {
        this.productId = productId;
        this.username = username;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        RatingCompositeKey compositeKey = (RatingCompositeKey) o;
        return (productId == compositeKey.productId) && (username.equals(compositeKey.username));
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, username);
    }

}
