package com.daiduong.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    // @Column(name = "username")
    @ManyToOne
    @JoinColumn(name = "username")
    private AccountEntity account;
    
}
