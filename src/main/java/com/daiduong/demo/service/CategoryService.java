package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    // get all category
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    // add new category
    public CategoryEntity addCategory(CategoryEntity category){
        String name = category.getName();
        if(name == null || name.length() == 0){
            throw new ApiRequestException("Fail to add category - Name not null or empty");
        }

        if(categoryRepository.count() < 1){
            category.setId(1);
        }
        else{
            int maxId = categoryRepository.findMaxId();
            category.setId(maxId + 1);
        }
        LocalDate currentDate = LocalDate.now();
        category.setCreateDate(currentDate);
        category.setUpdateDate(currentDate);
        return categoryRepository.save(category);
    }

    // upadate category
    public CategoryEntity updateCategory(int id, CategoryEntity category) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                "The category with id:" + id + " does not exist"
            ));

        String newName = category.getName();
        String newDes = category.getDescription();
        String oldName = categoryEntity.getName();
        String oldDes = categoryEntity.getDescription();
        boolean isUpdate = false;

        if(newName != null && newName.length() > 0 && 
            !newName.equals(oldName))
        {
            categoryEntity.setName(newName);
            isUpdate = true;
        }    

        if(newDes != null && newDes.length() > 0 &&
            !newDes.equals(oldDes))
        {
            categoryEntity.setDescription(newDes);
            isUpdate = true;
        }
        
        if(isUpdate)
        {
            categoryEntity.setUpdateDate(LocalDate.now());
        }

        return categoryRepository.save(categoryEntity);
    }

    // delete category
    public CategoryEntity deleteCategory(int id){
        CategoryEntity categoryEntity = categoryRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                "The category with id:" + id + " does not exist"
            ));
        if(categoryEntity.isDeleted() == false){
            categoryEntity.setDeleted(true);
            categoryEntity.setUpdateDate(LocalDate.now());
        }    
        return categoryRepository.save(categoryEntity);     
    }
}
