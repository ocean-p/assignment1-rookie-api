package com.daiduong.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public String hello(){
        return "<h1>This is Hello Page</h1>";
    }

    @GetMapping("/home")
    public String home(){
        return "<h1>This is Home Page</h1>";
    }
}
