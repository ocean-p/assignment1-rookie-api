package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.HomePageCustomerDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.dto.RatingDTO;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;
import com.daiduong.demo.service.interfaces.IRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IRatingService ratingService;

    @GetMapping("/product")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ProductPagingDTO> getNoDeletedProducts(@RequestParam int page) {
        ProductPagingDTO result = productService.getAllProductsNoDelete(page, "createDate");
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/product/search")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ProductPagingDTO> searchProducts(@RequestParam String name, @RequestParam int page){
        ProductPagingDTO result = productService.searchProductNoDeleteByName(name, page, "createDate");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ProductPagingDTO> getProductsByCategory(@RequestParam int id, @RequestParam int page){
        ProductPagingDTO result = productService.getProductNoDeleteByCategory(id, page, "createDate");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") int id){
        ProductDTO result = productService.getProductById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CategoryDTO>> getCategoryMenu(){
        List<CategoryDTO> result = categoryService.getCategoryMenu();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<HomePageCustomerDTO> getHomePage(@RequestParam int page){
        List<CategoryDTO> categoryList = categoryService.getCategoryMenu();
        ProductPagingDTO productPaging = productService.getAllProductsNoDelete(page, "createDate");

        HomePageCustomerDTO result = new HomePageCustomerDTO(categoryList, productPaging);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/product/rating")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> ratingProduct(@RequestBody RatingDTO ratingDTO){
        String message = ratingService.ratingProudct(ratingDTO);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/product/rating")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> updateRating(@RequestBody RatingDTO ratingDTO){
        String message = ratingService.updateRating(ratingDTO);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/product/rating/point")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Integer> ratingProductView(@RequestBody RatingDTO ratingDTO){
        int point = ratingService.viewPoint(ratingDTO);
        return ResponseEntity.ok(point);
    }
}
