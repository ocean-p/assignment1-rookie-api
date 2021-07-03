package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity addCategory(CategoryEntity category){
        int maxId = categoryRepository.findMaxId();
        category.setId(maxId + 1);
        LocalDate currentDate = LocalDate.now();
        category.setCreateDate(currentDate);
        category.setUpdateDate(currentDate);
        return categoryRepository.save(category);
    }
}
