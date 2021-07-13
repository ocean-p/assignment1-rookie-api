package com.daiduong.demo.repository;

import java.util.List;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.CartEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findByAccount(AccountEntity account);

    @Query(value = "select Max(cart_id) from cart",
            nativeQuery = true)
    Integer findMaxId();
}
