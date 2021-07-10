package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.HomePageCustomerDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping
    public String hello(){
        return "<h1>This is Customer page</h1>";
    }

    @GetMapping("/category")
    public List<CategoryDTO> getCategoryNoDelete(){
        return categoryService.getCategoryNoDelete();
    }

    @GetMapping("/product")
    public List<ProductDTO> getProductNoDeleteQuantityMoreZero(){
        return productService.getProductNoDeleteQuantityMoreZero();
    }

    @GetMapping("/product/category/{id}")
    public List<ProductDTO> getProductByCategory(@PathVariable("id") int categoryId){
        return productService.getProductByCategory(categoryId);
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable("id") int productId){
        return productService.getProductById(productId);
    }

    @GetMapping("/home")
    public HomePageCustomerDTO loadHomePageCustomer(){
        return productService.loadHomePageCustomer();
    }
}
