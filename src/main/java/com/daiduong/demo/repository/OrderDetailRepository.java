package com.daiduong.demo.repository;

import com.daiduong.composite.CompositeKey;
import com.daiduong.demo.entity.OrderDetailEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends 
                  JpaRepository<OrderDetailEntity, CompositeKey>{
    
}
