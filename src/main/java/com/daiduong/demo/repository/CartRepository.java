package com.daiduong.demo.repository;

import java.util.List;
import java.util.Optional;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.CartEntity;
import com.daiduong.demo.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    @Query(value = "select Max(cart_id) from cart", 
            nativeQuery = true)                
    Integer findMaxId();

    List<CartEntity> findByAccount(AccountEntity account);
    Page<CartEntity> findByAccount(AccountEntity account, Pageable page);
    Optional<CartEntity> findByAccountAndProduct(AccountEntity account, ProductEntity product);
}
