package com.daiduong.demo.service.interfaces;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService{
    // add account
    public AccountDTO addAccount(AccountDTO account);

    public AccountDTO getAccountByUserName(String username);

    // update account
    public AccountDTO updateCustomerAccountByAdmin(String username, AccountDTO newAccount);

    // delete account
    public String deleteCustomerAccountByAdmin(String username);

    public String restoreAccount(String username);

    public ListAccountPagingDTO getAllCustomerAccountsNoDelete(int pageNo);

    public ListAccountPagingDTO getAllAdminAccountsNoDelete(int pageNo);

    public ListAccountPagingDTO getCustomerAccountsNoDeleteBySearch(String value, int pageNo);

    public ListAccountPagingDTO getAllAccountsDeleted(int pageNo);
}
