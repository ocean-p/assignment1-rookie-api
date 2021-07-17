package com.daiduong.demo.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.daiduong.demo.convert.ProductConvert;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.repository.ProductRepository;
import com.daiduong.demo.service.interfaces.IProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {
    
    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    IProductService productService;

    @Autowired
    ProductConvert productConvert;

    List<ProductEntity> productEntityList;

    LocalDate currentDate = LocalDate.now();
    CategoryEntity categoryEntity1 = new CategoryEntity(
            1, "jacket", "jacket", currentDate, currentDate, false
        );

    @BeforeEach
    public void setUp() {
        ProductEntity productEntity1 = new ProductEntity(
            1, "product1", 100, "img", 10, "product1", 0,
            currentDate, currentDate, categoryEntity1, false
        );
        ProductEntity productEntity2 = new ProductEntity(
            2, "product2", 0, "img", 20, "product2", 0,
            currentDate, currentDate, categoryEntity1, false
        );
        productEntityList = new ArrayList<>();
        productEntityList.add(productEntity1);
        productEntityList.add(productEntity2);
    }

    @Test
    public void addProductTest(){

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(categoryEntity1));

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(productEntityList.get(0));

        ProductDTO param = productConvert.toDTO(productEntityList.get(0));
        ProductDTO result = productService.addProduct(param);    
        
        assertEquals(1, result.getId());
        assertEquals("product1", result.getName());
    }

    @Test
    public void addProductInCategoryCaseTest(){
        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(productEntityList.get(0));
        ProductDTO param = productConvert.toDTO(productEntityList.get(0));
        assertThrows(ApiRequestException.class, () -> {
            productService.addProduct(param);
        });
    }

    @Test
    public void addProductInFieldCaseTest(){
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(categoryEntity1));

        ProductDTO dto1 = new ProductDTO();

        ProductDTO dto2 = new ProductDTO();
        dto2.setName("");
        dto2.setPrice(100);
        dto2.setQuantity(10);

        ProductDTO dto3 = new ProductDTO();
        dto3.setName("dto3");
        dto3.setPrice(0);
        dto3.setQuantity(10);

        ProductDTO dto4 = new ProductDTO();
        dto3.setName("dto4");
        dto3.setPrice(100);
        dto3.setQuantity(0);

        assertThrows(ApiRequestException.class, () -> {
            productService.addProduct(dto1);
        });

        assertThrows(ApiRequestException.class, () -> {
            productService.addProduct(dto2);
        });

        assertThrows(ApiRequestException.class, () -> {
            productService.addProduct(dto3);
        });

        assertThrows(ApiRequestException.class, () -> {
            productService.addProduct(dto4);
        });
    }

    @Test
    public void getProductByIdTest() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(productEntityList.get(0)));

        ProductDTO productDTO = productService.getProductById(anyInt());

        assertEquals(1, productDTO.getId());
        assertEquals("product1", productDTO.getName());
    }

    @Test
    public void updateProductFailTest() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(productEntityList.get(1)));
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(categoryEntity1));

        assertThrows(ApiRequestException.class, () -> {
            productService.updateProduct(anyInt(), productConvert.toDTO(productEntityList.get(1)));
        });
           
    }
}
