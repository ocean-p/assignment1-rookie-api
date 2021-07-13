package com.daiduong.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class RatingEntity {
    
    @Id
    @Column(name = "rate_id")
    private int ratingId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "username")
    private AccountEntity account;

    @Column(name = "point")
    private int point;
    
    public RatingEntity() {
    }

    public RatingEntity(int ratingId, ProductEntity product, AccountEntity account, int point) {
        this.ratingId = ratingId;
        this.product = product;
        this.account = account;
        this.point = point;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
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
