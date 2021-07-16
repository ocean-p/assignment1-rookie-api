package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.ListAccountByRoleDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.service.interfaces.IAccountService;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/category/all")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category")
    public CategoryDTO addCategory(@RequestBody CategoryDTO dto) {
        return categoryService.addCategory(dto);
    }

    @PutMapping("/category/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") int id,
                                        @RequestBody CategoryDTO dto)
    {
        return categoryService.updateCategory(id, dto);
    }
    
    @DeleteMapping("/category/{id}")
    public CategoryDTO deleteCategory(@PathVariable("id") int id){
        return categoryService.deleteCategory(id);
    }
    /** END: CATEGORY */

    /** BEGIN: PRODUCT */
    @PostMapping("/product")
    public ProductDTO addProduct(@RequestBody ProductDTO dto){
        return productService.addProduct(dto);
    }

    @GetMapping("/product/all")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/product/{id}")
    public ProductDTO updateProduct(@PathVariable("id") int id, 
                                       @RequestBody ProductDTO dto)
    {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/product/{id}")
    public ProductDTO deleteProduct(@PathVariable("id") int id){
        return productService.deleteProduct(id);
    }
    /** END: PRODUCT */

    /** BEGIN: ACCOUNT */
    @PostMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountDTO addAccount(@RequestBody AccountDTO dto){
        return accountService.addAccount(dto);
    }

    @GetMapping("/account/all")
    public List<AccountDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountDTO updateAccount(@PathVariable("username") String username, 
                                       @RequestBody AccountDTO dto)
    {
        return accountService.updateCustomerAccountByAdmin(username, dto);
    }

    @DeleteMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountDTO deleteAccount(@PathVariable("username") String username){
        return accountService.deleteCustomerAccountByAdmin(username);
    }

    @GetMapping("/account/customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ListAccountByRoleDTO getAllCustomerAccounts(@RequestParam int page){
        return accountService.getAllCustomerAccounts(page);
    }

    @GetMapping("/account/ad")
    @PreAuthorize("hasRole('ADMIN')")
    public ListAccountByRoleDTO getAllAdminAccounts(@RequestParam int page){
        return accountService.getAllAdminAccounts(page);
    }
    /** END: ACCOUNT */
}
