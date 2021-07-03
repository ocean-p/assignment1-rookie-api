package com.daiduong.demo.repository;

import com.daiduong.demo.entity.ProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends 
                JpaRepository<ProductEntity, Integer>{
    
}
