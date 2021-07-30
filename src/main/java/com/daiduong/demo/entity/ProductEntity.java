package com.daiduong.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product",
    indexes = {
        @Index(name = "product_id_index", columnList = "product_id"),
        @Index(name = "product_name_index", columnList = "name"),
        @Index(name = "product_category_index", columnList = "category_id")
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "img")
    private String image;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "average_rate")
    private int averageRate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "is_delete")
    private boolean isDeleted;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<RatingEntity> ratingEntityList;

    public ProductEntity(int id, String name, float price, String image, int quantity, String description,
            int averageRate, LocalDate createDate, LocalDate updateDate, CategoryEntity category, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
        this.averageRate = averageRate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.category = category;
        this.isDeleted = isDeleted;
    }
    
    
}
