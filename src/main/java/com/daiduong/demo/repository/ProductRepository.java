package com.daiduong.demo.repository;

import java.util.List;
import java.util.Optional;

import com.daiduong.demo.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends 
                JpaRepository<ProductEntity, Integer>{
    
    @Query(value = "select Max(product_id) from product", 
            nativeQuery = true)                
    Integer findMaxId();

    @Query(value = "select * \n"
                + "from product\n"
                + "where is_delete = false\n"
                + "and quantity > 0\n"
                + "order by create_date desc\n", 
        nativeQuery = true)
    List<ProductEntity> getProductNoDeleteQuantityMoreZero();

    @Query(value = "select * \n"
                + "from product\n"
                + "where is_delete = false\n"
                + "and quantity > 0\n"
                + "and category_id = ?1\n"
                + "order by create_date desc\n", 
        nativeQuery = true)
    List<ProductEntity> getProductByCategory(int categoryId);

    @Query(value = "select * \n"
                + "from product\n"
                + "where is_delete = false\n"
                + "and quantity > 0\n"
                + "and product_id = ?1\n"
                + "order by create_date desc\n", 
        nativeQuery = true)
    Optional<ProductEntity> getProductById(int productId);

    Page<ProductEntity> findByIsDeleted(boolean isDelete, Pageable pageable);
}
