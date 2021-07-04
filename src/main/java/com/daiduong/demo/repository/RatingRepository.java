package com.daiduong.demo.repository;

import com.daiduong.demo.composite.RatingCompositeKey;
import com.daiduong.demo.entity.RatingEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends 
                    JpaRepository<RatingEntity, RatingCompositeKey> {
    
}
