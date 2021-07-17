package com.daiduong.demo.repositorytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class ProductRepositoryTest {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void saveTest() {
        int id;
        if(productRepository.count() < 1){
            id = 1;
        }
        else{
            id = productRepository.findMaxId() + 1;
        }
        
        LocalDate currentDate = LocalDate.now();
        CategoryEntity categoryEntity = categoryRepository.findById(1).orElseThrow();
        
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName("Rainbow T-shirt");
        productEntity.setPrice(500);
        productEntity.setImage("img");
        productEntity.setQuantity(20);
        productEntity.setDescription("Rainbow T-shirt");
        productEntity.setAverageRate(0);
        productEntity.setCreateDate(currentDate);
        productEntity.setUpdateDate(currentDate);
        productEntity.setCategory(categoryEntity);
        productEntity.setDeleted(false);

        productEntity = productRepository.save(productEntity);

        assertEquals("Rainbow T-shirt", productEntity.getName());
    }

    @Test
    public void findMaxIdTest() {
        int maxId = productRepository.findMaxId();
        assertEquals(15, maxId);
    }

    @Test
    public void findAllTest(){
        List<ProductEntity> list = productRepository.findAll();
        assertEquals(15, list.size());
    }

    @Test
    public void updateTest(){
        ProductEntity entity = productRepository.findById(15).orElseThrow();
        entity.setDescription("hello");
        entity = productRepository.save(entity);

        assertEquals(15, entity.getId());
        assertEquals("hello", entity.getDescription());
    }

    @Test
    public void findByIdTest() {
        boolean isExist = productRepository.findById(15).isPresent();
        assertEquals(true, isExist);
    }

    @Test
    public void notFoundTest() {
        assertThrows(ApiRequestException.class, () -> {
            productRepository.findById(100).orElseThrow(() -> new ApiRequestException(
                "Product not found"
            ));
        });
    }

    @Test
    public void findByIsDeletedTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("updateDate").descending());
        Page<ProductEntity> page = productRepository.findByIsDeleted(false, pageable);

        boolean isFailed = false;
        List<ProductEntity> list = page.getContent();
        for (ProductEntity productEntity : list) {
            if(productEntity.isDeleted()) {
                isFailed = true;
            }
        }

        assertEquals(false, isFailed);
    }

    @Test
    public void findByCategoryAndIsDeletedTest(){
        CategoryEntity category = categoryRepository.findById(1).orElseThrow();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("updateDate").descending());
        Page<ProductEntity> page = productRepository.findByCategoryAndIsDeleted(category, false, pageable);
    
        boolean isFailed = false;
        List<ProductEntity> list = page.getContent();
        for (ProductEntity productEntity : list) {
            if(productEntity.isDeleted() || productEntity.getCategory().getId() != 1){
                isFailed = true;
            }
        }
        assertEquals(false, isFailed);
    }

}
