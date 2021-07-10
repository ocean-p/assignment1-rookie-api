package com.daiduong.demo.composite;

import java.io.Serializable;

import com.daiduong.demo.entity.OrderEntity;
import com.daiduong.demo.entity.ProductEntity;

public class OrderDetailCompositeKey implements Serializable{
    private OrderEntity order;
    private ProductEntity product;
    
    public OrderDetailCompositeKey() {
    }

    public OrderDetailCompositeKey(OrderEntity order, ProductEntity product) {
        this.order = order;
        this.product = product;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
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
