package com.daiduong.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @GetMapping
    public String hello(){
        return "<h1>This is Customer page</h1>";
    }
}
