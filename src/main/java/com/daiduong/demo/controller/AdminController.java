package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.dto.ResponseDTO;
import com.daiduong.demo.exception.SuccessCode;
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

    @Autowired
    private SuccessCode successCode;

    /** BEGIN: CATEGORY */
    @GetMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getCategoryById(@PathVariable("id") int id){
        CategoryDTO dto = categoryService.getCategoryById(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(dto);
        response.setSuccessCode(successCode.getLOAD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addCategory(@RequestBody CategoryDTO dto) {
        CategoryDTO categoryDTO = categoryService.addCategory(dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(categoryDTO);
        response.setSuccessCode(successCode.getADD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateCategory(@PathVariable("id") int id,
                                                    @RequestBody CategoryDTO dto)
    {   CategoryDTO categoryDTO = categoryService.updateCategory(id, dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(categoryDTO);
        response.setSuccessCode(successCode.getUPDATE_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory(@PathVariable("id") int id){
        String message = categoryService.deleteCategory(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getDELETE_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/category/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> restoreCategory(@PathVariable("id") int id){
        String message = categoryService.restoreCategory(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getRESTORE_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCategoriesNoDelete(@RequestParam int page){
        CategoryPagingDTO result = categoryService.getAllCategoriesNoDelete(page);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCategoriesDeleted(@RequestParam int page){
        CategoryPagingDTO result = categoryService.getAllCategoriesDeleted(page);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> searchCategoryNoDeleted(@RequestParam String value, 
                                                               @RequestParam  int page)
    {
        CategoryPagingDTO result = categoryService.searchCategoryNoDeleted(value, page);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getCategoryMenu(){
        List<CategoryDTO> result = categoryService.getCategoryMenu();
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }
    /** END: CATEGORY */

    /** BEGIN: PRODUCT */
    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addProduct(@RequestBody ProductDTO dto){
        ProductDTO result = productService.addProduct(dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getADD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable("id") int id, 
                                                    @RequestBody ProductDTO dto)
    {
        ProductDTO result = productService.updateProduct(id, dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getUPDATE_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable("id") int id){
        String message = productService.deleteProduct(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getDELETE_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/product/restore/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> restoreProduct(@PathVariable("id") int id){
        String message = productService.restoreProduct(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getRESTORE_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllProductsNoDelete(@RequestParam int page){
        ProductPagingDTO result = productService.getAllProductsNoDelete(page, "updateDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllProductsDeleted(@RequestParam int page){
        ProductPagingDTO result = productService.getAllProductsDeleted(page, "updateDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> searchProductNoDeleteByName(@RequestParam String value, 
                                                                        @RequestParam int page)
    {
        ProductPagingDTO result = productService.searchProductNoDeleteByName(value, page, "updateDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable("id") int id){
        ProductDTO result = productService.getProductById(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getProductNoDeleteByCategory(@RequestParam int id, 
                                                        @RequestParam int page)
    {
        ProductPagingDTO result = productService.getProductNoDeleteByCategory(id, page, "updateDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }                                                    
    /** END: PRODUCT */

    /** BEGIN: ACCOUNT */
    @PostMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addAccount(@RequestBody AccountDTO dto){
        AccountDTO result = accountService.addAccount(dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getADD_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAccountByUsername(@PathVariable("username") String username){
        AccountDTO result = accountService.getAccountByUserName(username);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateAccount(@PathVariable("username") String username, 
                                                    @RequestBody AccountDTO dto)
    {
        AccountDTO result = accountService.updateCustomerAccountByAdmin(username, dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getUPDATE_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/account/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCustomerAccount(@PathVariable("username") String username){
        String message = accountService.deleteCustomerAccountByAdmin(username);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getDELETE_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account/restore/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> restoreAccount(@PathVariable("username") String username){
        String message = accountService.restoreCustomerAccount(username);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getRESTORE_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }     

    @GetMapping("/account/customer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCustomerAccountsNoDelete(@RequestParam int page){
        ResponseDTO response = new ResponseDTO();
        ListAccountPagingDTO result = accountService.getAllCustomerAccountsNoDelete(page);
        response.setSuccessCode(successCode.getLOAD_ACCOUNT_SUCCESS());
        response.setDatas(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/ad")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllAdminAccountsNoDelete(@RequestParam int page){
        ListAccountPagingDTO result = accountService.getAllAdminAccountsNoDelete(page);
        ResponseDTO response = new ResponseDTO();
        response.setSuccessCode(successCode.getLOAD_ACCOUNT_SUCCESS());
        response.setDatas(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getCustomerAccountsNoDeleteBySearch(@RequestParam String value, 
                                                                                    @RequestParam int page)
    {
        ListAccountPagingDTO result = accountService.getCustomerAccountsNoDeleteBySearch(value, page);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/account/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllAccountsDeleted(@RequestParam int page){
        ListAccountPagingDTO result = accountService.getAllAccountsDeleted(page);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_ACCOUNT_SUCCESS());
        return ResponseEntity.ok(response);
    }
    /** END: ACCOUNT */
}
