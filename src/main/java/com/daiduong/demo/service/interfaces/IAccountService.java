package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.entity.AccountEntity;

public interface IAccountService {
    // add account
    public AccountEntity addAccount(AccountEntity account);

    // get all account
    public List<AccountEntity> getAllAccounts(); 

    // update account
    public AccountEntity updateAccount(String username, AccountEntity newAccount);

    // delete account
    public AccountEntity deleteAccount(String username);
}
