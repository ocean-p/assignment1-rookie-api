package com.daiduong.demo.repository;

import com.daiduong.demo.entity.CategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends 
                JpaRepository<CategoryEntity, Integer> {
    
    @Query(value = "select Max(category_id) from category", 
            nativeQuery = true)                
    Integer findMaxId();
}
