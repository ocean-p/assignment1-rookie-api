package com.daiduong.demo.repositorytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.AccountRepository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void saveTest() {
        LocalDate currentDate = LocalDate.now();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("hello");
        accountEntity.setPassword("hello");
        accountEntity.setFullName("hello");
        accountEntity.setPhone("0123456789");
        accountEntity.setAddress("TP.HCM");
        accountEntity.setCreateDate(currentDate);
        accountEntity.setUpdateDate(currentDate);
        accountEntity.setRole("CUSTOMER");
        accountEntity.setDeleted(false);

        accountEntity = accountRepository.save(accountEntity);
        assertEquals("hello", accountEntity.getUsername());
    }

    @Test
    public void findByIdTest() {
        boolean isExist = accountRepository.findById("hello").isPresent();
        assertEquals(true, isExist);
    }

    @Test
    public void updateTest() {
        AccountEntity accountEntity = accountRepository.findById("hello").orElseThrow();
        accountEntity.setFullName("Hello Weekend");
        accountEntity = accountRepository.save(accountEntity);

        assertEquals("hello", accountEntity.getUsername());
        assertEquals("Hello Weekend", accountEntity.getFullName());
    }

    @Test
    public void notFoundTest() { 
        assertThrows(ApiRequestException.class, () -> {
            accountRepository.findById("zzzzzzz")
                .orElseThrow(() -> new ApiRequestException(
                    "This username not found"
                ));
        });
    }

    @Test
    public void findAllTest() {
        List<AccountEntity> accountEntityList = accountRepository.findAll();
        assertEquals(10, accountEntityList.size());
    }

    @Test
    public void findByIsDeletedTest(){
        Pageable pageable = PageRequest.of(0, 5, Sort.by("updateDate").descending());
        Page<AccountEntity> page = accountRepository.findByIsDeleted(true, pageable);

        boolean isFailed = false;
        List<AccountEntity> accountEntityList = page.getContent();
        for (AccountEntity accountEntity : accountEntityList) {
            if(accountEntity.isDeleted() == false){
                isFailed = true;
            }
        }

        assertEquals(false, isFailed);
    }
}
