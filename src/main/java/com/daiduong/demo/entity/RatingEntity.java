package com.daiduong.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.daiduong.demo.composite.RatingCompositeKey;

@Entity
@Table(name = "rating")
@IdClass(RatingCompositeKey.class)
public class RatingEntity {
    
    @Id
    //@Column(name = "product_id")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Id
    //@Column(name = "username")
    @ManyToOne
    @JoinColumn(name = "username")
    private AccountEntity account;

    @Column(name = "point")
    private int point;
    
    public RatingEntity() {
    }

    public RatingEntity(ProductEntity product, AccountEntity account, int point) {
        this.product = product;
        this.account = account;
        this.point = point;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    
}
