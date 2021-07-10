package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.CategoryDTO;

public interface ICategoryService {
    
    // get all category
    public List<CategoryDTO> getAllCategories();

    // add new category
    public CategoryDTO addCategory(CategoryDTO category);

    // upadate category
    public CategoryDTO updateCategory(int id, CategoryDTO category);

    // delete category
    public CategoryDTO deleteCategory(int id);

    // list category no delete 
    public List<CategoryDTO> getCategoryNoDelete();
}
