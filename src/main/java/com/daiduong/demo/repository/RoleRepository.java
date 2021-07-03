package com.daiduong.demo.repository;

import com.daiduong.demo.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends 
                JpaRepository<RoleEntity, Integer>{
    
}
