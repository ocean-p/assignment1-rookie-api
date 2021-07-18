package com.daiduong.demo.repository;

import java.util.List;
import java.util.Optional;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.entity.RatingEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {
    
    @Query(value = "select Max(rate_id) from rating", 
            nativeQuery = true)                
    Integer findMaxId();

    List<RatingEntity> findByProduct(ProductEntity productEntity);
    Optional<RatingEntity> findByProductAndAccount(ProductEntity productEntity, AccountEntity AccountEntity);
}
