package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.service.AccountService;
import com.daiduong.demo.service.CategoryService;
import com.daiduong.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    /** BEGIN: CATEGORY */
    @GetMapping("/category")
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category/new")
    public CategoryEntity addCategory(@RequestBody CategoryEntity entity) {
        return categoryService.addCategory(entity);
    }

    @PutMapping("/category/new/{id}")
    public CategoryEntity updateCategory(@PathVariable("id") int id,
                                        @RequestBody CategoryEntity entity)
    {
        return categoryService.updateCategory(id, entity);
    }
    
    @DeleteMapping("/category/deletion/{id}")
    public CategoryEntity deleteCategory(@PathVariable("id") int id){
        return categoryService.deleteCategory(id);
    }
    /** END: CATEGORY */

    /** BEGIN: PRODUCT */
    @PostMapping("/product/new")
    public ProductEntity addProduct(@RequestBody ProductEntity entity){
        return productService.addProduct(entity);
    }

    @GetMapping("/product")
    public List<ProductEntity> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/product/new/{id}")
    public ProductEntity updateProduct(@PathVariable("id") int id, 
                                       @RequestBody ProductEntity entity)
    {
        return productService.updateProduct(id, entity);
    }

    @DeleteMapping("/product/deletion/{id}")
    public ProductEntity deleteProduct(@PathVariable("id") int id){
        return productService.deleteProduct(id);
    }
    /** END: PRODUCT */

    /** BEGIN: ACCOUNT */
    @PostMapping("/account/new")
    public AccountEntity addAccount(@RequestBody AccountEntity entity){
        return accountService.addAccount(entity);
    }

    @GetMapping("/account")
    public List<AccountEntity> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/account/new/{username}")
    public AccountEntity updateAccount(@PathVariable("username") String username, 
                                       @RequestBody AccountEntity entity)
    {
        return accountService.updateAccount(username, entity);
    }

    @DeleteMapping("/account/deletion/{username}")
    public AccountEntity deleteAccount(@PathVariable("username") String username){
        return accountService.deleteAccount(username);
    }
    /** END: ACCOUNT */
}
