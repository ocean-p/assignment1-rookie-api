package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.convert.CategoryConvert;
import com.daiduong.demo.convert.ProductConvert;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.HomePageCustomerDTO;
import com.daiduong.demo.dto.PagingProductDTO;
import com.daiduong.demo.dto.ProductDTO;
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
    private CategoryConvert categoryConvert;

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
        if(categoryEntity.isDeleted()){
            throw new ApiRequestException("The category was deleted");
        }            

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
        
        List<ProductEntity> entityList = productRepository.findAll();
        
        return productConvert.toDTOList(entityList);
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
            if(categoryEntity.isDeleted()){
                throw new ApiRequestException("The category was deleted");
            }

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

    @Override
    public List<ProductDTO> getProductNoDeleteQuantityMoreZero() {
        
        List<ProductEntity> entityList = productRepository.getProductNoDeleteQuantityMoreZero();
        
        return productConvert.toDTOList(entityList);
    }

    @Override
    public List<ProductDTO> getProductByCategory(int categoryId) {
        List<ProductEntity> entityList = productRepository.getProductByCategory(categoryId);
        
        return productConvert.toDTOList(entityList);
    }

    @Override
    public ProductDTO getProductById(int productId) {
        ProductEntity productEntity = productRepository.getProductById(productId)
                    .orElseThrow(() -> new ApiRequestException(
                        "Product " + productId + " not found , out of stock or was deleted"
                    ));
        ProductDTO productDTO = productConvert.toDTO(productEntity);            
        return productDTO;
    }

    @Override
    public HomePageCustomerDTO loadHomePageCustomer() {
        // list product
        List<ProductEntity> productEntityList = productRepository.getProductNoDeleteQuantityMoreZero();
        List<ProductDTO> productDTOList = productConvert.toDTOList(productEntityList);
        
        // list category
        List<CategoryEntity> categoryEntityList = categoryRepository.getCategoryNoDelete();
        List<CategoryDTO> categoryDTOList = categoryConvert.toDTOList(categoryEntityList);
        
        HomePageCustomerDTO home = new HomePageCustomerDTO(categoryDTOList, productDTOList);

        return home;
    }

    @Override
    public PagingProductDTO pagingProductNoDelete(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by("createDate").descending());
        Page<ProductEntity> page = productRepository.findByIsDeleted(false, pageable);

        List<ProductEntity> productEntityList = page.getContent();
        List<ProductDTO> productDTOList = productConvert.toDTOList(productEntityList);

        PagingProductDTO pagingProductDTO = new PagingProductDTO();
        pagingProductDTO.setCurrentPage(pageNo);
        pagingProductDTO.setTotalPages(page.getTotalPages());
        pagingProductDTO.setTotalItems(page.getTotalElements());
        pagingProductDTO.setProductList(productDTOList);

        return pagingProductDTO;
    }
}
