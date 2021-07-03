package com.daiduong.demo.repository;

import com.daiduong.demo.entity.CategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends 
                JpaRepository<CategoryEntity, Integer> {
    
}
