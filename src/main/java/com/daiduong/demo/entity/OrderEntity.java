package com.daiduong.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
    
    @Id
    @Column(name = "order_id")
    private int id;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "payment")
    private String payment;

    @Column(name = "username")
    private String username;
    
    public OrderEntity() {
    }

    public OrderEntity(int id, LocalDate createDate, int totalQuantity, float totalPrice, String payment,
            String username) {
        this.id = id;
        this.createDate = createDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
