package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import com.daiduong.demo.convert.AccountConvert;
import com.daiduong.demo.convert.ListAccountPagingConvert;
import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.exception.ErrorCode;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.service.interfaces.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ErrorCode errorCode;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountConvert accountConvert;

    @Autowired
    private ListAccountPagingConvert pagingConvert;

    @Override
    public AccountDTO addAccount(AccountDTO account) {
        String username = account.getUsername();
        String password = account.getPassword();
        String fullName = account.getFullName();
        String phone = account.getPhone();
        String address = account.getAddress();
        String role = account.getRole();

        if(username == null || username.trim().length() == 0){
            throw new ApiRequestException(errorCode.getUSERNAME_IS_EMPTY());
        }

        Optional<AccountEntity> optional = accountRepository.findById(username);
        if(optional.isPresent()){
            throw new ApiRequestException(errorCode.getUSERNAME_ALREADY_TAKEN());
        }

        if(password == null || password.trim().length() < 6 || password.trim().length() > 20){
            throw new ApiRequestException(errorCode.getPASSWORD_NOT_CORRECT_FORMAT());
        }

        if(fullName == null || fullName.trim().length() == 0){
            throw new ApiRequestException(errorCode.getFULLNAME_IS_EMPTY());
        }

        if(phone == null || phone.trim().length() < 10 
            || phone.trim().length() > 11 || !phone.matches("^[0-9]+$"))
        {
            throw new ApiRequestException(errorCode.getPHONE_NOT_CORRECT_FORMAT());
        }

        if(address == null || address.trim().length() == 0){
            throw new ApiRequestException(errorCode.getADDRESS_IS_EMPTY());
        }

        if(role == null || 
            (!role.trim().equalsIgnoreCase("admin") && !role.trim().equalsIgnoreCase("customer")))
        {
            throw new ApiRequestException(errorCode.getROLE_NOT_CORRECT());
        }
        
        AccountEntity accountEntity = accountConvert.toEntity(account);

        String encodedPassword = passwordEncoder.encode(account.getPassword());
        accountEntity.setPassword(encodedPassword);

        LocalDate currentDate = LocalDate.now();
        accountEntity.setCreateDate(currentDate);
        accountEntity.setUpdateDate(currentDate);
        accountEntity.setRole("ROLE_" + role.toUpperCase());
        accountEntity = accountRepository.save(accountEntity);
        return accountConvert.toDTO(accountEntity);
    }


    @Override
    public AccountDTO updateCustomerAccountByAdmin(String username, AccountDTO newAccount){

        AccountEntity oldAccount = accountRepository.findById(username)
                                   .orElseThrow(() -> new ApiRequestException(
                                       errorCode.getACCOUNT_NOT_FOUND()
                                    ));
        if(!oldAccount.getRole().equalsIgnoreCase("ROLE_CUSTOMER")){
            throw new ApiRequestException(errorCode.getACCOUNT_NOT_BELONG_TO_CUSTOMER());
        }

        if(oldAccount.isDeleted()){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        String newFullName = newAccount.getFullName();
        String newPhone = newAccount.getPhone();
        String newAddress = newAccount.getAddress();
        String newPassword = newAccount.getPassword();
        String newRole = newAccount.getRole();

        if(newPassword == null 
            || newPassword.trim().length() < 6 
            || newPassword.trim().length() > 20)
        {
            throw new ApiRequestException(errorCode.getPASSWORD_NOT_CORRECT_FORMAT());
        }

        if(newFullName == null || newFullName.trim().length() == 0){
            throw new ApiRequestException(errorCode.getFULLNAME_IS_EMPTY());
        }

        if(newPhone == null || newPhone.trim().length() < 10 
            || newPhone.trim().length() > 11 || !newPhone.matches("^[0-9]+$"))
        {
            throw new ApiRequestException(errorCode.getPHONE_NOT_CORRECT_FORMAT());
        }

        if(newAddress == null || newAddress.trim().length() == 0){
            throw new ApiRequestException(errorCode.getADDRESS_IS_EMPTY());
        }

        if(newRole == null || newRole.trim().length() == 0 
            || (!newRole.equalsIgnoreCase("CUSTOMER") && !newRole.equalsIgnoreCase("ADMIN")))
        {
            throw new ApiRequestException(errorCode.getROLE_NOT_CORRECT());
        }    

        oldAccount.setFullName(newFullName);
        oldAccount.setPhone(newPhone);
        oldAccount.setAddress(newAddress);
        oldAccount.setPassword(passwordEncoder.encode(newPassword));
        oldAccount.setRole("ROLE_" + newRole.toUpperCase());
        oldAccount.setUpdateDate(LocalDate.now());
        oldAccount = accountRepository.save(oldAccount);
        return accountConvert.toDTO(oldAccount);
    }

    @Override
    public String deleteCustomerAccountByAdmin(String username){
        AccountEntity account = accountRepository.findById(username)
                                .orElseThrow(() -> new ApiRequestException(
                                    errorCode.getACCOUNT_NOT_FOUND()
                                ));
        if(!account.getRole().equalsIgnoreCase("ROLE_CUSTOMER")){
            throw new ApiRequestException(errorCode.getACCOUNT_NOT_BELONG_TO_CUSTOMER());
        }

        if(account.isDeleted() == true){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        account.setDeleted(true);
        account.setUpdateDate(LocalDate.now());
        account = accountRepository.save(account);
        return "Delete Successfully!";
    }

    @Override
    public String restoreCustomerAccount(String username){
        AccountEntity account = accountRepository.findById(username)
                                .orElseThrow(() -> new ApiRequestException(
                                    errorCode.getACCOUNT_NOT_FOUND()
                                ));
        if(!account.getRole().equalsIgnoreCase("ROLE_CUSTOMER")){
            throw new ApiRequestException(errorCode.getACCOUNT_NOT_BELONG_TO_CUSTOMER());
        }
        
        if(account.isDeleted() == false){
            throw new ApiRequestException(errorCode.getACCOUNT_ACTIVE());
        }

        account.setDeleted(false);
        account.setUpdateDate(LocalDate.now());
        account = accountRepository.save(account);
        return "Restore Successfully!";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Username not found"
            )); 
    }

    @Override
    public ListAccountPagingDTO getAllCustomerAccountsNoDelete(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<AccountEntity> page = accountRepository.findByRoleAndIsDeleted("ROLE_CUSTOMER", false, pageable);

        ListAccountPagingDTO result = pagingConvert.convert(pageNo, page);
        return result;
    }

    @Override
    public ListAccountPagingDTO getAllAdminAccountsNoDelete(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }

        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<AccountEntity> page = accountRepository.findByRoleAndIsDeleted("ROLE_ADMIN", false, pageable);

        ListAccountPagingDTO result = pagingConvert.convert(pageNo, page);
        return result;
    }

    @Override
    public ListAccountPagingDTO getCustomerAccountsNoDeleteBySearch(String value, int pageNo) {
        if(value == null || value.trim().length() == 0){
            throw new ApiRequestException(errorCode.getSEARCH_VALUE_IS_EMPTY());
        }
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<AccountEntity> page = accountRepository.findByUsernameContainingAndRoleAndIsDeleted(
                                        value, "ROLE_CUSTOMER", false, pageable);

        ListAccountPagingDTO result = pagingConvert.convert(pageNo, page);
        return result;
    }

    @Override
    public ListAccountPagingDTO getAllAccountsDeleted(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }

        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<AccountEntity> page = accountRepository.findByIsDeleted(true, pageable);

        ListAccountPagingDTO result = pagingConvert.convert(pageNo, page);
        return result;
    }


    @Override
    public AccountDTO getAccountByUserName(String username) {
        AccountEntity accountEntity = accountRepository.findById(username)
                            .orElseThrow(() -> new ApiRequestException(
                                errorCode.getACCOUNT_NOT_FOUND()));
        AccountDTO result = accountConvert.toDTO(accountEntity);
        return result;                        
    }

}
