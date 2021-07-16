package com.daiduong.demo.repositorytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRepositoryTest {
    
    @Autowired
    CategoryRepository categoryRepository;

    
    @Test
    public void saveCategoryTest() {
        int id;
        if(categoryRepository.count() < 1){
            id = 1;
        }
        else{
            id = categoryRepository.findMaxId() + 1;
        }
        LocalDate currentDate = LocalDate.now();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setName("sport");
        categoryEntity.setDescription("summer");
        categoryEntity.setCreateDate(currentDate);
        categoryEntity.setUpdateDate(currentDate);
        categoryEntity.setDeleted(false);

        categoryEntity = categoryRepository.save(categoryEntity);

        assertEquals("sport", categoryEntity.getName());
    }

    @Test
    public void updateCategoryTest() {
        CategoryEntity categoryEntity = categoryRepository.findById(5).orElseThrow();
        categoryEntity.setDescription("ss");
        categoryEntity = categoryRepository.save(categoryEntity);

        assertEquals(5, categoryEntity.getId());
        assertEquals("ss", categoryEntity.getDescription());
    }

    @Test
    public void findMaxIdTest() {
        int maxId = categoryRepository.findMaxId();

        assertEquals(11, maxId);
    }

    @Test
    public void findAllCategoriesTest() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        assertEquals(11, categoryEntityList.size());
    }

    @Test
    public void findCategoryByIdTest() {
        boolean isExist = categoryRepository.findById(5).isPresent();

        assertEquals(true, isExist);
    }

    @Test
    public void getCategoryNoDeleteTest() {
        boolean isDelete = false;
        List<CategoryEntity> list = categoryRepository.getCategoryNoDelete();
        for (CategoryEntity categoryEntity : list) {
            if(categoryEntity.isDeleted() == true) {
                isDelete = true;
            }
        }
        assertEquals(false, isDelete);
    }

    @Test
    public void notFoundById(){
        assertThrows(ApiRequestException.class, () -> {
            categoryRepository.findById(100)
            .orElseThrow(() -> new ApiRequestException(
                "Category 100 not found"
            ));
        });
    }
}
