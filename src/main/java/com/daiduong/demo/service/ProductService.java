package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.daiduong.demo.convert.ProductConvert;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.repository.ProductRepository;
import com.daiduong.demo.service.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductConvert productConvert;

    @Override
    public ProductDTO addProduct(ProductDTO product) {
        String name = product.getName();
        float price = product.getPrice();
        int quantity = product.getQuantity();
        int categoryId = product.getCategoryId();

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ApiRequestException(
                        "Fail to add product - The categoryId:"
                        + categoryId 
                        + " does not exist"
                    ));

        if(name == null || name.length() == 0 || price <= 0 || quantity <= 0){
            throw new ApiRequestException("Fail to add product - try again");
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
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> dtoList = new ArrayList<>();
        List<ProductEntity> entityList = productRepository.findAll();
        for (ProductEntity product : entityList) {
            ProductDTO dto = productConvert.toDTO(product);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO product) {
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
        int oldCategoryId = productEntity.getCategory().getId();

        boolean isUpdate = false;

        if(newCategoryId > 0 && newCategoryId != oldCategoryId){
            CategoryEntity categoryEntity = categoryRepository.findById(newCategoryId)
                            .orElseThrow(() -> new ApiRequestException(
                                "The category " + id + " doesn not exist"
                            ));
            productEntity.setCategory(categoryEntity);   
            isUpdate = true;             
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

        productEntity = productRepository.save(productEntity);
        return productConvert.toDTO(productEntity);
    }

    @Override
    public ProductDTO deleteProduct(int id){
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        "The product with id:" + id + " does not exist"
                    ));
        if(productEntity.isDeleted() == false){
            productEntity.setDeleted(true);
            productEntity.setUpdateDate(LocalDate.now());
        }
        productEntity = productRepository.save(productEntity);
        return productConvert.toDTO(productEntity);            
    }
}
