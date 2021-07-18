package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.HomePageCustomerDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.dto.RatingDTO;
import com.daiduong.demo.payload.response.MessageResponse;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;
import com.daiduong.demo.service.interfaces.IRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ProductPagingDTO getNoDeletedProducts(@RequestParam int page) {
        return productService.getAllProductsNoDelete(page, "createDate");
    }
    
    @GetMapping("/product/search")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ProductPagingDTO searchProducts(@RequestParam String name, @RequestParam int page){
        return productService.searchProductNoDeleteByName(name, page, "createDate");
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ProductPagingDTO getProductsByCategory(@RequestParam int id, @RequestParam int page){
        return productService.getProductNoDeleteByCategory(id, page, "createDate");
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ProductDTO getProductById(@PathVariable("id") int id){
        return productService.getProductById(id);
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<CategoryDTO> getCategoryMenu(){
        return categoryService.getCategoryMenu();
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('CUSTOMER')")
    public HomePageCustomerDTO getHomePage(@RequestParam int page){
        List<CategoryDTO> categoryList = categoryService.getCategoryMenu();
        ProductPagingDTO productPaging = productService.getAllProductsNoDelete(page, "createDate");

        return new HomePageCustomerDTO(categoryList, productPaging);
    }

    @PostMapping("/product/rating")
    @PreAuthorize("hasRole('CUSTOMER')")
    public MessageResponse ratingProduct(@RequestBody RatingDTO ratingDTO){
        return new MessageResponse(ratingService.ratingProudct(ratingDTO));
    }

    @PostMapping("/product/rating/point")
    @PreAuthorize("hasRole('CUSTOMER')")
    public MessageResponse ratingProductView(@RequestBody RatingDTO ratingDTO){
        return new MessageResponse(ratingService.viewPoint(ratingDTO));
    }
}
