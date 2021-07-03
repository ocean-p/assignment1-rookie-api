package com.daiduong.demo.composite;

import java.io.Serializable;
import java.util.Objects;

public class CompositeKey implements Serializable{
    private int orderId;
    private int productId;
    
    public CompositeKey() {
    }

    public CompositeKey(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

    @Override
    public boolean equals(Object o) {
       if(this == o) return true;
       if(o == null || getClass() != o.getClass()) return false;
       CompositeKey ck = (CompositeKey) o;
       return (orderId == ck.orderId) && (productId == ck.productId);
    }

    
}
