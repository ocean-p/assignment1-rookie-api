package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.service.interfaces.IAccountService;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") int id){
        CategoryDTO dto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO dto) {
        CategoryDTO categoryDTO = categoryService.addCategory(dto);
        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") int id,
                                                    @RequestBody CategoryDTO dto)
    {   CategoryDTO categoryDTO = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(categoryDTO);
    }
    
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id){
        String message = categoryService.deleteCategory(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/category/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreCategory(@PathVariable("id") int id){
        String message = categoryService.restoreCategory(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryPagingDTO> getAllCategoriesNoDelete(@RequestParam int page){
        CategoryPagingDTO result = categoryService.getAllCategoriesNoDelete(page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryPagingDTO> getAllCategoriesDeleted(@RequestParam int page){
        CategoryPagingDTO result = categoryService.getAllCategoriesDeleted(page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryPagingDTO> searchCategoryNoDeleted(@RequestParam String value, 
                                                                     @RequestParam  int page)
    {
        CategoryPagingDTO result = categoryService.searchCategoryNoDeleted(value, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryDTO>> getCategoryMenu(){
        List<CategoryDTO> result = categoryService.getCategoryMenu();
        return ResponseEntity.ok(result);
    }
    /** END: CATEGORY */

    /** BEGIN: PRODUCT */
    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO dto){
        ProductDTO result = productService.addProduct(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") int id, 
                                                    @RequestBody ProductDTO dto)
    {
        ProductDTO result = productService.updateProduct(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id){
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/product/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreProduct(@PathVariable("id") int id){
        String message = productService.restoreProduct(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductPagingDTO> getAllProductsNoDelete(@RequestParam int page){
        ProductPagingDTO result = productService.getAllProductsNoDelete(page, "updateDate");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductPagingDTO> getAllProductsDeleted(@RequestParam int page){
        ProductPagingDTO result = productService.getAllProductsDeleted(page, "updateDate");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductPagingDTO> searchProductNoDeleteByName(@RequestParam String value, 
                                                                        @RequestParam int page)
    {
        ProductPagingDTO result = productService.searchProductNoDeleteByName(value, page, "updateDate");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") int id){
        ProductDTO result = productService.getProductById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductPagingDTO> getProductNoDeleteByCategory(@RequestParam int id, 
                                                        @RequestParam int page)
    {
        ProductPagingDTO result = productService.getProductNoDeleteByCategory(id, page, "updateDate");
        return ResponseEntity.ok(result);
    }                                                    
    /** END: PRODUCT */

    /** BEGIN: ACCOUNT */
    @PostMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO dto){
        AccountDTO result = accountService.addAccount(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> getAccountByUsername(@PathVariable("username") String username){
        AccountDTO result = accountService.getAccountByUserName(username);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("username") String username, 
                                                    @RequestBody AccountDTO dto)
    {
        AccountDTO result = accountService.updateCustomerAccountByAdmin(username, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCustomerAccount(@PathVariable("username") String username){
        String message = accountService.deleteCustomerAccountByAdmin(username);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/account/restore/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreAccount(@PathVariable("username") String username){
        String message = accountService.restoreCustomerAccount(username);
        return ResponseEntity.ok(message);
    }     

    @GetMapping("/account/customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListAccountPagingDTO> getAllCustomerAccountsNoDelete(@RequestParam int page){
        ListAccountPagingDTO result = accountService.getAllCustomerAccountsNoDelete(page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/account/ad")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListAccountPagingDTO> getAllAdminAccountsNoDelete(@RequestParam int page){
        ListAccountPagingDTO result = accountService.getAllAdminAccountsNoDelete(page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListAccountPagingDTO> getCustomerAccountsNoDeleteBySearch(@RequestParam String value, 
                                                                                    @RequestParam int page)
    {
        ListAccountPagingDTO result = accountService.getCustomerAccountsNoDeleteBySearch(value, page);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/account/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListAccountPagingDTO> getAllAccountsDeleted(@RequestParam int page){
        ListAccountPagingDTO result = accountService.getAllAccountsDeleted(page);
        return ResponseEntity.ok(result);
    }
    /** END: ACCOUNT */
}
