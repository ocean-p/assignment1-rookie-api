package com.daiduong.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.daiduong.demo.composite.OrderDetailCompositeKey;

@Entity
@Table(name = "category")
@IdClass(OrderDetailCompositeKey.class)
public class OrderDetailEntity {
    
    @Id
    @Column(name = "order_id")
    private int orderId;

    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private float totalPrice;

    public OrderDetailEntity() {
    }

    public OrderDetailEntity(int orderId, int productId, String productName, float unitPrice, int quantity,
            float totalPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

}
