package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.daiduong.demo.convert.AccountConvert;
import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.ListAccountByRoleDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.exception.ApiRequestException;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountConvert accountConvert;

    @Override
    public AccountDTO addAccount(AccountDTO account) {
        String username = account.getUsername();
        String password = account.getPassword();
        String fullName = account.getFullName();
        String phone = account.getPhone();
        String address = account.getAddress();
        String role = account.getRole();

        if(username == null || username.trim().length() == 0){
            throw new ApiRequestException("Username must not be null or empty");
        }

        Optional<AccountEntity> optional = accountRepository.findById(username);
        if(optional.isPresent()){
            throw new ApiRequestException("Username already used");
        }

        if(password == null || password.trim().length() < 6 || password.trim().length() > 20){
            throw new ApiRequestException("Password's length must be between 6 and 20");
        }

        if(fullName == null || fullName.trim().length() == 0){
            throw new ApiRequestException("Full name must not be null or empty");
        }

        if(phone == null || phone.trim().length() < 10 
            || phone.trim().length() > 11 || !phone.matches("^[0-9]+$"))
        {
            throw new ApiRequestException("Phone must be number and length 10 or 11");
        }

        if(address == null || address.trim().length() == 0){
            throw new ApiRequestException("Address must not be null or empty");
        }

        if(role == null || 
            (!role.trim().equalsIgnoreCase("admin") && !role.trim().equalsIgnoreCase("customer")))
        {
            throw new ApiRequestException("Role must be admin or customer");
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
    public List<AccountDTO> getAllAccounts(){
        
        List<AccountEntity> entityList = accountRepository.findAll();
        
        return accountConvert.toDTOList(entityList);
    }

    @Override
    public AccountDTO updateCustomerAccountByAdmin(String username, AccountDTO newAccount){

        AccountEntity oldAccount = accountRepository.findById(username)
                                   .orElseThrow(() -> new ApiRequestException(
                                       "Username not found"
                                    ));
        if(!oldAccount.getRole().equalsIgnoreCase("ROLE_CUSTOMER")){
            throw new ApiRequestException("This username isn't of Customer account");
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
            throw new ApiRequestException("Password's length must be between 6 and 20");
        }

        if(newFullName == null || newFullName.trim().length() == 0){
            throw new ApiRequestException("Full name must not be null or empty");
        }

        if(newPhone == null || newPhone.trim().length() < 10 
            || newPhone.trim().length() > 11 || !newPhone.matches("^[0-9]+$"))
        {
            throw new ApiRequestException("Phone must be number and length 10 or 11");
        }

        if(newAddress == null || newAddress.trim().length() == 0){
            throw new ApiRequestException("Address must not be null or empty");
        }

        if(newRole == null || newRole.trim().length() == 0 
            || (!newRole.equalsIgnoreCase("CUSTOMER") && !newRole.equalsIgnoreCase("ADMIN")))
        {
            throw new ApiRequestException("Role must be admin or customer");
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
    public AccountDTO deleteCustomerAccountByAdmin(String username){
        AccountEntity account = accountRepository.findById(username)
                                .orElseThrow(() -> new ApiRequestException(
                                    "Username not found"
                                ));
        if(!account.getRole().equalsIgnoreCase("ROLE_CUSTOMER")){
            throw new ApiRequestException("This username isn't of Customer account");
        }

        if(account.isDeleted() == false){
            account.setDeleted(true);
            account.setUpdateDate(LocalDate.now());
        }
        account = accountRepository.save(account);
        return accountConvert.toDTO(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Username not found"
            )); 
    }

    @Override
    public ListAccountByRoleDTO getAllCustomerAccounts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("createDate").descending());
        Page<AccountEntity> page = accountRepository.findByRole("ROLE_CUSTOMER", pageable);

        List<AccountEntity> accountEntityList = page.getContent();
        List<AccountDTO> accountDTOList = accountConvert.toDTOList(accountEntityList);

        ListAccountByRoleDTO result = new ListAccountByRoleDTO();
        result.setCurrentPage(pageNo);
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        result.setAccountDTOList(accountDTOList);

        return result;
    }

    @Override
    public ListAccountByRoleDTO getAllAdminAccounts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("createDate").descending());
        Page<AccountEntity> page = accountRepository.findByRole("ROLE_ADMIN", pageable);

        List<AccountEntity> accountEntityList = page.getContent();
        List<AccountDTO> accountDTOList = accountConvert.toDTOList(accountEntityList);

        ListAccountByRoleDTO result = new ListAccountByRoleDTO();
        result.setCurrentPage(pageNo);
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        result.setAccountDTOList(accountDTOList);

        return result;
    }

}
