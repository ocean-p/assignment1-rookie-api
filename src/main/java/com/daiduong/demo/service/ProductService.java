package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // add new product
    public ProductEntity addProduct(ProductEntity product) {
        String name = product.getName();
        float price = product.getPrice();
        int quantity = product.getQuantity();
        int categoryId = product.getCategoryId();

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if(!categoryEntity.isPresent()){
            throw new ApiRequestException("Fail to add product - The categoryId: " 
                                            + categoryId 
                                            + " does not exist");
        }

        if(name == null || name.length() == 0 || price <= 0 || quantity <= 0){
            throw new ApiRequestException("Fail to add product - try again");
        }

        if(productRepository.count() < 1){
            product.setId(1);
        }
        else{
            int maxId = productRepository.findMaxId();
            product.setId(maxId + 1);
        }
        product.setAverageRate(0);
        LocalDate currentDate = LocalDate.now();
        product.setCreateDate(currentDate);
        product.setUpdateDate(currentDate);
        return productRepository.save(product);
    }
}
