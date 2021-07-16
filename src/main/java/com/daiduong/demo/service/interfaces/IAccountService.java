package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.ListAccountByRoleDTO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService{
    // add account
    public AccountDTO addAccount(AccountDTO account);

    // get all account
    public List<AccountDTO> getAllAccounts(); 

    // update account
    public AccountDTO updateCustomerAccountByAdmin(String username, AccountDTO newAccount);

    // delete account
    public AccountDTO deleteCustomerAccountByAdmin(String username);

    public ListAccountByRoleDTO getAllCustomerAccounts(int pageNo);

    public ListAccountByRoleDTO getAllAdminAccounts(int pageNo);
}
