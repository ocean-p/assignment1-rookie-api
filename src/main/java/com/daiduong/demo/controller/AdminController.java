package com.daiduong.demo.controller;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.payload.response.MessageResponse;
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
    @GetMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO getCategoryById(@PathVariable("id") int id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO addCategory(@RequestBody CategoryDTO dto) {
        return categoryService.addCategory(dto);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO updateCategory(@PathVariable("id") int id,
                                        @RequestBody CategoryDTO dto)
    {
        return categoryService.updateCategory(id, dto);
    }
    
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteCategory(@PathVariable("id") int id){
        return new MessageResponse(categoryService.deleteCategory(id));
    }

    @PostMapping("/category/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse restoreCategory(@PathVariable("id") int id){
        return new MessageResponse(categoryService.restoreCategory(id));
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryPagingDTO getAllCategoriesNoDelete(@RequestParam int page){
        return categoryService.getAllCategoriesNoDelete(page);
    }

    @GetMapping("/category/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryPagingDTO getAllCategoriesDeleted(@RequestParam int page){
        return categoryService.getAllCategoriesDeleted(page);
    }

    @GetMapping("/category/search")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryPagingDTO searchCategoryNoDeleted(@RequestParam String value, 
                                                    @RequestParam  int page)
    {
        return categoryService.searchCategoryNoDeleted(value, page);
    }
    /** END: CATEGORY */

    /** BEGIN: PRODUCT */
    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO addProduct(@RequestBody ProductDTO dto){
        return productService.addProduct(dto);
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO updateProduct(@PathVariable("id") int id, 
                                    @RequestBody ProductDTO dto)
    {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteProduct(@PathVariable("id") int id){
        return new MessageResponse(productService.deleteProduct(id));
    }

    @PostMapping("/product/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse restoreProduct(@PathVariable("id") int id){
        return new MessageResponse(productService.restoreProduct(id));
    }

    @GetMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductPagingDTO getAllProductsNoDelete(@RequestParam int page){
        return productService.getAllProductsNoDelete(page);
    }

    @GetMapping("/product/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductPagingDTO getAllProductsDeletedDto(@RequestParam int page){
        return productService.getAllProductsDeleted(page);
    }

    @GetMapping("/product/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductPagingDTO searchProductNoDeleteByName(@RequestParam String value, 
                                                        @RequestParam int page)
    {
        return productService.searchProductNoDeleteByName(value, page);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO getProductById(@PathVariable("id") int id){
        return productService.getProductById(id);
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductPagingDTO getProductNoDeleteByCategory(@RequestParam int id, 
                                                        @RequestParam int page)
    {
        return productService.getProductNoDeleteByCategory(id, page);
    }                                                    
    /** END: PRODUCT */

    /** BEGIN: ACCOUNT */
    @PostMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountDTO addAccount(@RequestBody AccountDTO dto){
        return accountService.addAccount(dto);
    }

    @GetMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountDTO getAccountByUsername(@PathVariable("username") String username){
        return accountService.getAccountByUserName(username);
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
    public MessageResponse deleteCustomerAccount(@PathVariable("username") String username){
        return new MessageResponse(accountService.deleteCustomerAccountByAdmin(username));
    }

    @PostMapping("/account/restore/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse restoreAccount(@PathVariable("username") String username){
        return new MessageResponse(accountService.restoreCustomerAccount(username));
    }     

    @GetMapping("/account/customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ListAccountPagingDTO getAllCustomerAccountsNoDelete(@RequestParam int page){
        return accountService.getAllCustomerAccountsNoDelete(page);
    }

    @GetMapping("/account/ad")
    @PreAuthorize("hasRole('ADMIN')")
    public ListAccountPagingDTO getAllAdminAccountsNoDelete(@RequestParam int page){
        return accountService.getAllAdminAccountsNoDelete(page);
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ListAccountPagingDTO getCustomerAccountsNoDeleteBySearch(@RequestParam(required = true) String value, 
                                                    @RequestParam int page)
    {
        return accountService.getCustomerAccountsNoDeleteBySearch(value, page);
    }
    
    @GetMapping("/account/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ListAccountPagingDTO getAllAccountsDeleted(@RequestParam int page){
        return accountService.getAllAccountsDeleted(page);
    }
    /** END: ACCOUNT */
}
