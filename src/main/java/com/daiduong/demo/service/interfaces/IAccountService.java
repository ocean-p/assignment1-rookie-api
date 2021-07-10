package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService{
    // add account
    public AccountDTO addAccount(AccountDTO account);

    // get all account
    public List<AccountDTO> getAllAccounts(); 

    // update account
    public AccountDTO updateAccount(String username, AccountDTO newAccount);

    // delete account
    public AccountDTO deleteAccount(String username);

    // register account
    public String registerAccount(AccountDTO account);
}
