package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.entity.AccountEntity;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService{
    // add account
    public AccountEntity addAccount(AccountEntity account);

    // get all account
    public List<AccountEntity> getAllAccounts(); 

    // update account
    public AccountEntity updateAccount(String username, AccountEntity newAccount);

    // delete account
    public AccountEntity deleteAccount(String username);

    // register account
    public String registerAccount(AccountEntity account);
}
