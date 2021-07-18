package com.daiduong.demo.repository;

import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
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

    Page<ProductEntity> findByIsDeleted(boolean isDeleted, Pageable pageable);
    Page<ProductEntity> findByNameContainingAndIsDeleted(String name, boolean isDeleted, Pageable pageable);
    Page<ProductEntity> findByCategoryAndIsDeleted(CategoryEntity category, boolean isDeleted, Pageable pageable);

    List<ProductEntity> findByCategoryAndIsDeleted(CategoryEntity category, boolean isDeleted);
}
