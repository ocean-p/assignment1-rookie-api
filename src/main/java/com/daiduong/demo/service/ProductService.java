package com.daiduong.demo.service;

import java.time.LocalDate;

import com.daiduong.demo.convert.ProductConvert;
import com.daiduong.demo.convert.ProductPagingConvert;

import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.exception.ErrorCode;
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
    private ErrorCode errorCode;

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
            .orElseThrow(() -> new ApiRequestException(errorCode.getCATEGORY_NOT_FOUND()));
        
        if(categoryEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getCATEGORY_IS_DISABLED());
        }            

        if(name == null || name.trim().length() == 0){
            throw new ApiRequestException(errorCode.getNAME_IS_EMPTY());
        }

        if(img == null || img.trim().length() == 0){
            throw new ApiRequestException(errorCode.getIMAGEURL_IS_EMPTY());
        }

        if(String.valueOf(price) == null || !String.valueOf(price).matches("^[0-9]+(\\.[0-9]+){0,1}$") 
            || price <= 0)
        {
            throw new ApiRequestException(errorCode.getPRICE_LESS_THAN_ZERO());
        }

        if(String.valueOf(quantity) == null || !String.valueOf(quantity).matches("^[0-9]+$")
            || quantity <= 0)
        {
            throw new ApiRequestException(errorCode.getQUANTITY_LESS_THAN_ZERO());
        }
        try{
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
        catch(Exception e) {
            throw new ApiRequestException(errorCode.getADD_PRODUCT_ERR());
        }
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO product) {
        ProductEntity productEntity = productRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                    errorCode.getPRODUCT_NOT_FOUND()));
        
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }            

        int newCategoryId = product.getCategoryId();
        CategoryEntity categoryEntity = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new ApiRequestException(errorCode.getCATEGORY_NOT_FOUND()));

        if(categoryEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getCATEGORY_IS_DISABLED());
        }        

        String newName = product.getName();
        String newImg = product.getImage();
        String newDes = product.getDescription();
        float newPrice = product.getPrice();
        int newQuantity = product.getQuantity();

        if(newName == null || newName.trim().length() == 0){
            throw new ApiRequestException(errorCode.getNAME_IS_EMPTY());
        }
        if(newImg == null || newImg.trim().length() == 0){
            throw new ApiRequestException(errorCode.getIMAGEURL_IS_EMPTY());
        }
        if(newDes == null || newDes.trim().length() == 0){
            throw new ApiRequestException(errorCode.getDESCRIPTION_IS_EMPTY());
        }
        if(String.valueOf(newPrice) == null 
            || !String.valueOf(newPrice).matches("^[0-9]+(\\.[0-9]+){0,1}$")  
            || newPrice <= 0)
        {
            throw new ApiRequestException(errorCode.getPRICE_LESS_THAN_ZERO());
        }
        if(String.valueOf(newQuantity) == null 
            || !String.valueOf(newQuantity).matches("^[0-9]+$")
            || newQuantity <= 0)
        {
            throw new ApiRequestException(errorCode.getQUANTITY_LESS_THAN_ZERO());
        }
        try{
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
        catch (Exception e) {
            throw new ApiRequestException(errorCode.getUPDATE_PRODUCT_ERR());
        }
    }

    @Override
    public String deleteProduct(int id){
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        errorCode.getPRODUCT_NOT_FOUND()
                    ));
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }
        try{
            productEntity.setDeleted(true);
            productEntity.setUpdateDate(LocalDate.now());
            productEntity = productRepository.save(productEntity);

            return "Delete Successfully!";
        }
        catch(Exception e){
            throw new ApiRequestException(errorCode.getDELETE_PRODUCT_ERR());
        }         
    }

    @Override
    public String restoreProduct(int id) {
        ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException(
                        errorCode.getPRODUCT_NOT_FOUND()
                    ));
        if(productEntity.isDeleted() == false){
            throw new ApiRequestException(errorCode.getPRODUCT_ACTIVE());
        }
        try{
            productEntity.setDeleted(false);
            productEntity.setUpdateDate(LocalDate.now());
            productEntity = productRepository.save(productEntity);

            CategoryEntity categoryEntity = productEntity.getCategory();
            if(categoryEntity.isDeleted()){
                categoryEntity.setDeleted(false);
                categoryEntity.setUpdateDate(LocalDate.now());
                categoryRepository.save(categoryEntity);
            }

            return "Restore Successfully!";
        }
        catch (Exception e) {
            throw new ApiRequestException(errorCode.getRESTORE_PRODUCT_ERR());
        }  
    }

    @Override
    public ProductPagingDTO getAllProductsNoDelete(int pageNo, String valueSort) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        try{
            Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by(valueSort).descending());
            Page<ProductEntity> page = productRepository.findByIsDeleted(false, pageable);
            
            return productPagingConvert.convert(pageNo, page);
        }
        catch (Exception e){
            throw new ApiRequestException(errorCode.getLOAD_PRODUCT_ERR());
        }
    }

    @Override
    public ProductPagingDTO getAllProductsDeleted(int pageNo, String valueSort) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        try{
            Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by(valueSort).descending());
            Page<ProductEntity> page = productRepository.findByIsDeleted(true, pageable);
            
            return productPagingConvert.convert(pageNo, page);
        }
        catch (Exception e){
            throw new ApiRequestException(errorCode.getLOAD_PRODUCT_ERR());
        }
    }

    @Override
    public ProductPagingDTO searchProductNoDeleteByName(String value, int pageNo, String valueSort) {
        if(value == null || value.trim().length() == 0){
            throw new ApiRequestException(errorCode.getSEARCH_VALUE_IS_EMPTY());
        }
        
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        try{
            Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by(valueSort).descending());
            Page<ProductEntity> page = productRepository.findByNameContainingAndIsDeleted(
                                                    value, false, pageable);
            return productPagingConvert.convert(pageNo, page);
        }
        catch (Exception e) {
            throw new ApiRequestException(errorCode.getSEARCH_PRODUCT_ERR());
        }                                        
    }

    @Override
    public ProductDTO getProductById(int id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(errorCode.getPRODUCT_NOT_FOUND()));
        
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }        
        return productConvert.toDTO(productEntity);        
    }

    @Override
    public ProductPagingDTO getProductNoDeleteByCategory(int categoryId, int pageNo, String valueSort) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiRequestException(errorCode.getCATEGORY_NOT_FOUND()));
        if(category.isDeleted()){
            throw new ApiRequestException(errorCode.getCATEGORY_IS_DISABLED());
        }        
        
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        try{
            Pageable pageable = PageRequest.of(pageNo - 1, 10, Sort.by(valueSort).descending());
            Page<ProductEntity> page = productRepository.findByCategoryAndIsDeleted(
                                        category, false, pageable);
            return productPagingConvert.convert(pageNo, page);
        }
        catch (Exception e) {
            throw new ApiRequestException(errorCode.getLOAD_PRODUCT_ERR());
        }
    }    

    
}
