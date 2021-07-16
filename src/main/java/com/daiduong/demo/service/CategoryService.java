package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.convert.CategoryConvert;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.service.interfaces.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService{
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConvert categoryConvert;

    @Override
    public List<CategoryDTO> getAllCategories() {
        
        List<CategoryEntity> entityList = categoryRepository.findAll();
        
        return categoryConvert.toDTOList(entityList);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO category){
        String name = category.getName();
        if(name == null || name.length() == 0){
            throw new ApiRequestException("Fail to add category - Name not null or empty");
        }

        CategoryEntity categoryEntity = categoryConvert.toEntity(category);

        if(categoryRepository.count() < 1){
            categoryEntity.setId(1);
        }
        else{
            int maxId = categoryRepository.findMaxId();
            categoryEntity.setId(maxId + 1);
        }
        LocalDate currentDate = LocalDate.now();
        categoryEntity.setCreateDate(currentDate);
        categoryEntity.setUpdateDate(currentDate);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConvert.toDTO(categoryEntity);
    }

    @Override
    public CategoryDTO updateCategory(int id, CategoryDTO category) {
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
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConvert.toDTO(categoryEntity);
    }

    @Override
    public CategoryDTO deleteCategory(int id){
        CategoryEntity categoryEntity = categoryRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                "The category with id:" + id + " does not exist"
            ));
        if(categoryEntity.isDeleted() == false){
            categoryEntity.setDeleted(true);
            categoryEntity.setUpdateDate(LocalDate.now());
        }
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConvert.toDTO(categoryEntity);     
    }

    @Override
    public List<CategoryDTO> getCategoryNoDelete() {
       
        List<CategoryEntity> entityList = categoryRepository.getCategoryNoDelete();
        
        return categoryConvert.toDTOList(entityList);
    }
}
