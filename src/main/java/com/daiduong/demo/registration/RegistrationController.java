package com.daiduong.demo.registration;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.service.interfaces.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    
    @Autowired
    private IAccountService accountService;
    
    @PostMapping
    public String register(@RequestBody AccountDTO account){
        return accountService.registerAccount(account);
    }
}
