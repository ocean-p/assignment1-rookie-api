package com.daiduong.demo.service;

import java.time.LocalDate;

import com.daiduong.demo.convert.ProductConvert;
import com.daiduong.demo.convert.ProductPagingConvert;

import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.repository.ProductRepository;
import com.daiduong.demo.service.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductConvert productConvert;

    @Autowired
    private ProductPagingConvert productPagingConvert;

    @Override
    public ProductDTO addProduct(ProductDTO product) {
        String name = product.getName();
        float price = product.getPrice();
        int quantity = product.getQuantity();
        int categoryId = product.getCategoryId();
        String img = product.getImage();

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiRequestException("The category " + categoryId + " not found"));
        
        if(categoryEntity.isDeleted()){
            throw new ApiRequestException("The category was deleted");
        }            

        if(name == null || name.length() == 0){
            throw new ApiRequestException("ERR: Name is empty");
        }

        if(img == null || img.length() == 0){
            throw new ApiRequestException("ERR: ImageUrl is empty");
        }

        if(String.valueOf(price) == null || price <= 0){
            throw new ApiRequestException("ERR: Price < 0");
        }

        if(String.valueOf(quantity) == null || quantity <= 0){
            throw new ApiRequestException("ERR: Quantity < 0");
        }

        ProductEntity productEntity = productConvert.toEntity(product);

        if(productRepository.count() < 1){
            productEntity.setId(1);
        }
        else{
            int maxId = productRepository.findMaxId();
            productEntity.setId(maxId + 1);
        }
        productEntity.setAverageRate(0);
        LocalDate currentDate = LocalDate.now();
        productEntity.setCreateDate(currentDate);
        productEntity.setUpdateDate(currentDate);
        productEntity.setCategory(categoryEntity);
        productEntity = productRepository.save(productEntity);
        return productConvert.toDTO(productEntity);
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO product) {
        ProductEntity productEntity = productRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                    "The product " + id + " not found"));
        
        if(productEntity.isDeleted()){
            throw new ApiRequestException("The product was deleted");
        }            

        int newCategoryId = product.getCategoryId();
        CategoryEntity categoryEntity = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new ApiRequestException("The category not found"));

        if(categoryEntity.isDeleted()){
            throw new ApiRequestException("The category was deleted");
        }        

        String newName = product.getName();
        String newImg = product.getImage();
        String newDes = product.getDescription();
        float newPrice = product.getPrice();
        int newQuantity = product.getQuantity();

        if(newName == null || newName.length() == 0){
            throw new ApiRequestException("ERR: Name is empty");
        }
        if(newImg == null || newImg.length() == 0){
            throw new ApiRequestException("ERR: ImageURL is empty");
        }
        if(newDes == null || newDes.length() == 0){
            throw new ApiRequestException("ERR: Description is empty");
        }
        if(String.valueOf(newPrice) == null || newPrice <= 0){
            throw new ApiRequestException("ERR: Price < 0");
        }
        if(String.valueOf(newQuantity) == null || newQuantity <= 0){
            throw new ApiRequestException("ERR: Quantity < 0");
        }
        
        productEntity.setName(newName);
        productEntity.setImage(newImg);
        productEntity.setDescription(newDes);
        productEntity.setPrice(newPrice);
        productEntity.setQuantity(newQuantity);
        productEntity.setCategory(categoryEntity);
        productEntity.setUpdateDate(LocalDate.now());
        productEntity = productRepository.save(productEntity);
        return productConvert.toDTO(productEntity);
    }

    @Override
    public String deleteProduct(int id){
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        "The product " + id + " not found"
                    ));
        if(productEntity.isDeleted()){
            throw new ApiRequestException("The product already deleted");
        }
        
        productEntity.setDeleted(true);
        productEntity.setUpdateDate(LocalDate.now());
        productEntity = productRepository.save(productEntity);

        return "Delete Successfully!";         
    }

    @Override
    public String restoreProduct(int id) {
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        "The product " + id + " not found"
                    ));
        if(productEntity.isDeleted() == false){
            throw new ApiRequestException("The product already active");
        }
        
        productEntity.setDeleted(false);
        productEntity.setUpdateDate(LocalDate.now());
        productEntity = productRepository.save(productEntity);

        return "Restore Successfully!";  
    }

    @Override
    public ProductPagingDTO getAllProductsNoDelete(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("updateDate").descending());
        Page<ProductEntity> page = productRepository.findByIsDeleted(false, pageable);
        
        return productPagingConvert.convert(pageNo, page);
    }

    @Override
    public ProductPagingDTO getAllProductsDeleted(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("updateDate").descending());
        Page<ProductEntity> page = productRepository.findByIsDeleted(true, pageable);
        
        return productPagingConvert.convert(pageNo, page);
    }

    @Override
    public ProductPagingDTO searchProductNoDeleteByName(String value, int pageNo) {
        if(value == null || value.length() == 0){
            throw new ApiRequestException("ERR: Value is empty");
        }
        
        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("updateDate").descending());
        Page<ProductEntity> page = productRepository.findByNameContainingAndIsDeleted(
                                                value, false, pageable);
        return productPagingConvert.convert(pageNo, page);                                        
    }

    @Override
    public ProductDTO getProductById(int id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("The product not found"));
        return productConvert.toDTO(productEntity);        
    }

    @Override
    public ProductPagingDTO getProductNoDeleteByCategory(int categoryId, int pageNo) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiRequestException("The category not found"));
        
        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }

        Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by("updateDate").descending());
        Page<ProductEntity> page = productRepository.findByCategoryAndIsDeleted(
                                    category, false, pageable);
        return productPagingConvert.convert(pageNo, page);
    }

    

    
}
