package com.daiduong.demo.repository;

import com.daiduong.demo.entity.AccountEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends 
                JpaRepository<AccountEntity, String>{
     Boolean existsByUsername(String username);

     Page<AccountEntity> findByRoleAndIsDeleted(String role, boolean isDeleted, Pageable pageable);
     Page<AccountEntity> findByUsernameContainingAndRoleAndIsDeleted(String username, 
                              String role, boolean isDeleted, Pageable pageable);
                              
     Page<AccountEntity> findByIsDeleted(boolean isDeleted, Pageable pageable);
                              
}
