package com.daiduong.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.daiduong.demo.composite.RatingCompositeKey;

@Entity
@Table(name = "rating")
@IdClass(RatingCompositeKey.class)
public class RatingEntity {
    
    @Id
    @Column(name = "product_id")
    private int productId;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "point")
    private int point;
    
    public RatingEntity() {
    }

    public RatingEntity(int productId, String username, int point) {
        this.productId = productId;
        this.username = username;
        this.point = point;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
