package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;
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

    // get all products
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // update products
    public ProductEntity updateProduct(int id, ProductEntity product) {
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        "The product with id:" + id + " does not exist"
                    ));
        String newName = product.getName();
        String newImg = product.getImage();
        String newDes = product.getDescription();
        float newPrice = product.getPrice();
        int newQuantity = product.getQuantity();
        int newCategoryId = product.getCategoryId();
        
        String oldName = productEntity.getName();
        String oldImg = productEntity.getImage();
        String oldDes = productEntity.getDescription();
        float oldPrice = productEntity.getPrice();
        int oldQuantity = productEntity.getQuantity();
        int oldCategoryId = productEntity.getCategoryId();

        boolean isUpdate = false;

        if(newCategoryId != oldCategoryId){
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(newCategoryId);
            if(categoryEntity.isPresent()){
                productEntity.setCategoryId(newCategoryId);
                isUpdate = true;
            }
            else{
                throw new ApiRequestException("Fail to update the product " + id
                                            + " - The category " + newCategoryId 
                                            + " does not exist");
            }
        }

        if(newName != null && newName.length() > 0 && !newName.equals(oldName)){
            productEntity.setName(newName);
            isUpdate = true;
        }

        if(String.valueOf(newPrice) != null && newPrice != oldPrice && newPrice > 0){
            productEntity.setPrice(newPrice);
            isUpdate = true;
        }

        if(newImg != null && newImg.length() > 0 && !newImg.equals(oldImg)){
            productEntity.setImage(newImg);
            isUpdate = true;
        }

        if(newDes != null && newDes.length() > 0 && !newDes.equals(oldDes)){
            productEntity.setDescription(newDes);
            isUpdate = true;
        }

        if(String.valueOf(newQuantity) != null && newQuantity != oldQuantity && newQuantity > 0){
            productEntity.setQuantity(newQuantity);
            isUpdate = true;
        }

        if(isUpdate){
            productEntity.setUpdateDate(LocalDate.now());
        }

        return productRepository.save(productEntity);
    }

    // delete product
    public ProductEntity deleteProduct(int id){
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        "The product with id:" + id + " does not exist"
                    ));
        if(productEntity.isDeleted() == false){
            productEntity.setDeleted(true);
            productEntity.setUpdateDate(LocalDate.now());
        }
        return productRepository.save(productEntity);            
    }
}
