package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.service.interfaces.IAccountService;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;

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
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    /** BEGIN: CATEGORY */
    @GetMapping("/category")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category/new")
    public CategoryDTO addCategory(@RequestBody CategoryDTO dto) {
        return categoryService.addCategory(dto);
    }

    @PutMapping("/category/updating/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") int id,
                                        @RequestBody CategoryDTO dto)
    {
        return categoryService.updateCategory(id, dto);
    }
    
    @DeleteMapping("/category/deletion/{id}")
    public CategoryDTO deleteCategory(@PathVariable("id") int id){
        return categoryService.deleteCategory(id);
    }
    /** END: CATEGORY */

    /** BEGIN: PRODUCT */
    @PostMapping("/product/new")
    public ProductDTO addProduct(@RequestBody ProductDTO dto){
        return productService.addProduct(dto);
    }

    @GetMapping("/product")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/product/updating/{id}")
    public ProductDTO updateProduct(@PathVariable("id") int id, 
                                       @RequestBody ProductDTO dto)
    {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/product/deletion/{id}")
    public ProductDTO deleteProduct(@PathVariable("id") int id){
        return productService.deleteProduct(id);
    }
    /** END: PRODUCT */

    /** BEGIN: ACCOUNT */
    @PostMapping("/account/new")
    public AccountDTO addAccount(@RequestBody AccountDTO dto){
        return accountService.addAccount(dto);
    }

    @GetMapping("/account")
    public List<AccountDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/account/updating/{username}")
    public AccountDTO updateAccount(@PathVariable("username") String username, 
                                       @RequestBody AccountDTO dto)
    {
        return accountService.updateAccount(username, dto);
    }

    @DeleteMapping("/account/deletion/{username}")
    public AccountDTO deleteAccount(@PathVariable("username") String username){
        return accountService.deleteAccount(username);
    }
    /** END: ACCOUNT */
}
