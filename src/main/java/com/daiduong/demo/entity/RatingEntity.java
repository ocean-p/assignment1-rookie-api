package com.daiduong.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating",
    indexes = {
        @Index(name = "rating_id_index", columnList = "rate_id")
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
