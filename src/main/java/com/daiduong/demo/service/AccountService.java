package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.daiduong.demo.convert.AccountConvert;
import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.service.interfaces.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
        accountEntity.setPassword(encodedPassword);

        LocalDate currentDate = LocalDate.now();
        accountEntity.setCreateDate(currentDate);
        accountEntity.setUpdateDate(currentDate);
        accountEntity.setRole(role.toUpperCase());
        accountEntity = accountRepository.save(accountEntity);
        return accountConvert.toDTO(accountEntity);
    }

    @Override
    public List<AccountDTO> getAllAccounts(){
        
        List<AccountEntity> entityList = accountRepository.findAll();
        
        return accountConvert.toDTOList(entityList);
    }

    @Override
    public AccountDTO updateAccount(String username, AccountDTO newAccount){
        AccountEntity oldAccount = accountRepository.findById(username)
                                   .orElseThrow(() -> new ApiRequestException(
                                       "Username not found"
                                    ));
        String newPassword = newAccount.getPassword();
        String newFullName = newAccount.getFullName();
        String newPhone = newAccount.getPhone();
        String newAddress = newAccount.getAddress();
        String newRole = newAccount.getRole();
        
        String oldPassword = oldAccount.getPassword();
        String oldFullName = oldAccount.getFullName();
        String oldPhone = oldAccount.getPhone();
        String oldAddress = oldAccount.getAddress();
        String oldRole = oldAccount.getRole();

        boolean isUpdate = false;

        if(newPassword != null && newPassword.trim().length() > 0 && !newPassword.equals(oldPassword)){
            String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
            oldAccount.setPassword(encodedPassword);
            isUpdate = true;
        }

        if(newFullName != null && newFullName.trim().length() > 0 && !newFullName.equals(oldFullName)){
            oldAccount.setFullName(newFullName);
            isUpdate = true;
        }

        if(newPhone != null && (newPhone.trim().length() == 10 || newPhone.trim().length() == 11)
            && newPhone.matches("^[0-9]+$") && !newPhone.equals(oldPhone))
        {
            oldAccount.setPhone(newPhone);
            isUpdate = true;
        }
        
        if(newAddress != null && newAddress.trim().length() > 0 && !newAddress.equals(oldAddress)){
            oldAccount.setAddress(newAddress);
            isUpdate = true;
        }

        if(newRole != null && newRole.trim().length() > 0 
            && (newRole.trim().equalsIgnoreCase("admin") || newRole.trim().equalsIgnoreCase("customer"))
            && !newRole.equals(oldRole))
        {
            oldAccount.setRole(newRole.toUpperCase());
            isUpdate = true;
        }    

        if(isUpdate){
            oldAccount.setUpdateDate(LocalDate.now());
        }
        oldAccount = accountRepository.save(oldAccount);
        return accountConvert.toDTO(oldAccount);
    }

    @Override
    public AccountDTO deleteAccount(String username){
        AccountEntity account = accountRepository.findById(username)
                                .orElseThrow(() -> new ApiRequestException(
                                    "Username not found"
                                ));
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
    public String registerAccount(AccountDTO account) {
        String username = account.getUsername();
        String password = account.getPassword();
        String fullName = account.getFullName();
        String phone = account.getPhone();
        String address = account.getAddress();

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
        AccountEntity accountEntity = accountConvert.toEntity(account);
        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
        accountEntity.setPassword(encodedPassword);

        LocalDate currentDate = LocalDate.now();
        accountEntity.setCreateDate(currentDate);
        accountEntity.setUpdateDate(currentDate);

        accountEntity.setRole("CUSTOMER");
        accountRepository.save(accountEntity);
        return "Register Successfully!";
    }
}
