package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;

public interface ICategoryService {

    public CategoryDTO getCategoryById(int id);

    public CategoryDTO addCategory(CategoryDTO category);

    public CategoryDTO updateCategory(int id, CategoryDTO category);

    public String deleteCategory(int id);

    public String restoreCategory(int id);

    public CategoryPagingDTO getAllCategoriesNoDelete(int pageNo);

    public CategoryPagingDTO getAllCategoriesDeleted(int pageNo);

    public CategoryPagingDTO searchCategoryNoDeleted(String value, int pageNo);

    //for customer
    public List<CategoryDTO> getCategoryMenu();
}
