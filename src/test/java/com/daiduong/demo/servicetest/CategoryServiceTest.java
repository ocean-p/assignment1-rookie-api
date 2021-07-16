package com.daiduong.demo.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.daiduong.demo.convert.CategoryConvert;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.service.interfaces.ICategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CategoryServiceTest {
    
    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    CategoryConvert categoryConvert;

    List<CategoryEntity> categoryEntityList;

    @BeforeEach
    public void setUp() {
        LocalDate currentDate = LocalDate.now();
        CategoryEntity categoryEntity1 = new CategoryEntity(
            1, "jacket", "jacket", currentDate, currentDate, false
        );
        CategoryEntity categoryEntity2 = new CategoryEntity(
            2, "jeans", "jeans", currentDate, currentDate, false
        );
        categoryEntityList = new ArrayList<>();
        categoryEntityList.add(categoryEntity1);
        categoryEntityList.add(categoryEntity2);
    }

    @Test
    public void getAllCategoriesTest() {
        when(categoryRepository.findAll()).thenReturn(categoryEntityList);

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        assertEquals(2, categoryDTOList.size());
    }

    @Test
    public void addCategoryTest() {
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntityList.get(0));
        
        CategoryDTO param = categoryConvert.toDTO(categoryEntityList.get(0));
        CategoryDTO categoryDTO = categoryService.addCategory(param);

        assertEquals(1, categoryDTO.getId());
        assertEquals("jacket", categoryDTO.getName());
    }

    @Test
    public void addCategoryByNameCaseTest() {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setName("");

        assertThrows(ApiRequestException.class, () -> {
            categoryService.addCategory(categoryDTO1);
        });

        assertThrows(ApiRequestException.class, () -> {
            categoryService.addCategory(categoryDTO2);
        });
    }

    @Test
    public void updateCategoryByIdCaseTest() {
        
    }
}
