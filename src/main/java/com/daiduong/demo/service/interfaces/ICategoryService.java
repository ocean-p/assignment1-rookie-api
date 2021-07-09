package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;

public interface ICategoryService {
    
    // get all category
    public List<CategoryEntity> getAllCategories();

    // add new category
    public CategoryEntity addCategory(CategoryEntity category);

    // upadate category
    public CategoryEntity updateCategory(int id, CategoryEntity category);

    // delete category
    public CategoryEntity deleteCategory(int id);
}
