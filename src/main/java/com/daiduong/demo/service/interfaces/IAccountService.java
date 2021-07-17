package com.daiduong.demo.service.interfaces;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService{

    public AccountDTO addAccount(AccountDTO account);

    public AccountDTO getAccountByUserName(String username);

    public AccountDTO updateCustomerAccountByAdmin(String username, AccountDTO newAccount);

    public String deleteCustomerAccountByAdmin(String username);

    public String restoreCustomerAccount(String username);

    public ListAccountPagingDTO getAllCustomerAccountsNoDelete(int pageNo);

    public ListAccountPagingDTO getAllAdminAccountsNoDelete(int pageNo);

    public ListAccountPagingDTO getCustomerAccountsNoDeleteBySearch(String value, int pageNo);

    public ListAccountPagingDTO getAllAccountsDeleted(int pageNo);
}
