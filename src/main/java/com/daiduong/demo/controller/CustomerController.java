package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.dto.CartPagingDTO;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.dto.RatingDTO;
import com.daiduong.demo.dto.ResponseDTO;
import com.daiduong.demo.exception.SuccessCode;
import com.daiduong.demo.service.interfaces.ICartService;
import com.daiduong.demo.service.interfaces.ICategoryService;
import com.daiduong.demo.service.interfaces.IProductService;
import com.daiduong.demo.service.interfaces.IRatingService;

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
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private SuccessCode successCode;

    @GetMapping("/product")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getNoDeletedProducts(@RequestParam int page) {
        ProductPagingDTO result = productService.getAllProductsNoDelete(page, "createDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/product/search")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> searchProducts(@RequestParam String name, @RequestParam int page){
        ProductPagingDTO result = productService.searchProductNoDeleteByName(name, page, "createDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/category")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getProductsByCategory(@RequestParam int id, @RequestParam int page){
        ProductPagingDTO result = productService.getProductNoDeleteByCategory(id, page, "createDate");
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable("id") int id){
        ProductDTO result = productService.getProductById(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_PRODUCT_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getCategoryMenu(){
        List<CategoryDTO> result = categoryService.getCategoryMenu();
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CATEGORY_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/product/rating")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> ratingProduct(@RequestBody RatingDTO ratingDTO){
        String message = ratingService.ratingProudct(ratingDTO);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getADD_RATING_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/rating")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateRating(@RequestBody RatingDTO ratingDTO){
        String message = ratingService.updateRating(ratingDTO);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getUPDATE_RATING_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/product/rating/point")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> ratingProductView(@RequestBody RatingDTO ratingDTO){
        int point = ratingService.viewPoint(ratingDTO);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(point);
        response.setSuccessCode(successCode.getLOAD_RATING_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> viewCart(@RequestParam int page, 
                                                @RequestBody CartDTO dto)
    {
        CartPagingDTO result = cartService.viewCart(page, "id", dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CART_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO dto){
        String message = cartService.addToCart(dto);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getADD_TO_CART_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> getCartById(@PathVariable("id") int id){
        CartDTO result = cartService.getCartById(id);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(result);
        response.setSuccessCode(successCode.getLOAD_CART_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> deleteCartItem(@RequestBody CartDTO cartDTO){
        String message = cartService.deleteCartItem(cartDTO);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getDELETE_ITEM_SUCCESS());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart/all")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> deleteAllCartItems(@RequestBody CartDTO cartDTO){
        String message = cartService.deleteAllCartItems(cartDTO);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getDELETE_ITEM_SUCCESS());
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/cart/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDTO> updateQuantityInCart(@RequestBody CartDTO cartDTO){
        String message = cartService.updateQuantityInCart(cartDTO);
        ResponseDTO response = new ResponseDTO();
        response.setDatas(message);
        response.setSuccessCode(successCode.getUPDATE_QUANTITY_SUCCESS());
        return ResponseEntity.ok(response);
    }
}
