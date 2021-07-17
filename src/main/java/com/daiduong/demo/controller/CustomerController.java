package com.daiduong.demo.controller;

import com.daiduong.demo.service.interfaces.ICartService;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICartService cartService;

    @GetMapping
    public String hello(){
        return "<h1>This is Customer page</h1>";
    }

    // @GetMapping("/category")
    // public List<CategoryDTO> getCategoryNoDelete(){
    //     return categoryService.getCategoryNoDelete();
    // }

      
}
