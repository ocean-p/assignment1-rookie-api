package com.daiduong.demo.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.daiduong.demo.convert.AccountConvert;
import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.service.interfaces.IAccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AccountServiceTest {
    
    @MockBean
    AccountRepository accountRepository;

    @Autowired
    IAccountService accountService;

    @Autowired
    AccountConvert accountConvert;

    List<AccountEntity> accountEntityList;

    @BeforeEach
    public void setUp() {
        LocalDate currentDate = LocalDate.now();

        AccountEntity accountEntity1 = new AccountEntity(
            "hello", "123456", "Hello Weekend", "0123456789",
            "TP.HCM", currentDate, currentDate, "CUSTOMER", false
        );
        AccountEntity accountEntity2 = new AccountEntity(
            "helloo", "123456", "Hello Weekend", "0123456789",
            "TP.HCM", currentDate, currentDate, "CUSTOMER", false
        );
        accountEntityList = new ArrayList<>();
        accountEntityList.add(accountEntity1);
        accountEntityList.add(accountEntity2);
    }

    @Test
    public void addAccountTest() {
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(accountEntityList.get(0));

        AccountDTO param = accountConvert.toDTO(accountEntityList.get(0));
        AccountDTO accountDTO = accountService.addAccount(param);

        assertEquals("hello", accountDTO.getUsername());
    }

    @Test
    public void addAccountFailByPassword() {
        LocalDate currentDate = LocalDate.now();
        AccountEntity accountEntity = new AccountEntity(
            "hello", "hello", "Hello Weekend", "0123456789",
            "TP.HCM", currentDate, currentDate, "CUSTOMER", false
        );
        AccountDTO param = accountConvert.toDTO(accountEntity);

        assertThrows(ApiRequestException.class, () -> {
            accountService.addAccount(param);
        });
    }

    @Test
    public void addAccountFailByDuplicateUsername() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(accountEntityList.get(0)));
        AccountDTO param = accountConvert.toDTO(accountEntityList.get(0));
        assertThrows(ApiRequestException.class, () -> {
            accountService.addAccount(param);
        });
    }

    @Test
    public void getAllAccountsTest() {
        when(accountRepository.findAll()).thenReturn(accountEntityList);
        List<AccountDTO> accountDTOList = accountService.getAllAccounts();
        assertEquals(2, accountDTOList.size());
    }
}
