package com.daiduong.demo.service;

import java.time.LocalDate;

import com.daiduong.demo.convert.CategoryConvert;
import com.daiduong.demo.convert.CategoryPagingConvert;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.service.interfaces.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService{
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConvert categoryConvert;

    @Autowired
    private CategoryPagingConvert categoryPagingConvert;
    
    @Override
    public CategoryDTO getCategoryById(int id){
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(
            () -> new ApiRequestException("Category not found")
        );

        return categoryConvert.toDTO(entity);
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

        if(categoryEntity.isDeleted()){
            throw new ApiRequestException("The category was deleted");
        }
            
        String newName = category.getName();
        String newDes = category.getDescription();

        if(newName == null || newName.length() == 0)
        {
            throw new ApiRequestException("Name is empty");
        }    

        if(newDes == null || newDes.length() == 0)
        {
            throw new ApiRequestException("Description is empty");
        }
        
        categoryEntity.setName(newName);
        categoryEntity.setDescription(newDes);
        categoryEntity.setUpdateDate(LocalDate.now());
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConvert.toDTO(categoryEntity);
    }

    @Override
    public String deleteCategory(int id){
        CategoryEntity categoryEntity = categoryRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                "The category not found"
            ));
        if(categoryEntity.isDeleted()){
            throw new ApiRequestException("This category already deleted");
        }

        categoryEntity.setDeleted(true);
        categoryEntity.setUpdateDate(LocalDate.now());
        categoryEntity = categoryRepository.save(categoryEntity);

        return "Delete Successfully!";     
    }

    @Override
    public String restoreCategory(int id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
            .orElseThrow(() -> new ApiRequestException(
                "The category not found"
            ));
        if(categoryEntity.isDeleted() == false){
            throw new ApiRequestException("This category already active");
        }

        categoryEntity.setDeleted(false);
        categoryEntity.setUpdateDate(LocalDate.now());
        categoryEntity = categoryRepository.save(categoryEntity);

        return "Restore Successfully!";
    }

    @Override
    public CategoryPagingDTO getAllCategoriesNoDelete(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }
        
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<CategoryEntity> page = categoryRepository.findByIsDeleted(false, pageable);

        return categoryPagingConvert.convert(pageNo, page);
    }

    @Override
    public CategoryPagingDTO getAllCategoriesDeleted(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }
        
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<CategoryEntity> page = categoryRepository.findByIsDeleted(true, pageable);

        return categoryPagingConvert.convert(pageNo, page);
    }

    @Override
    public CategoryPagingDTO searchCategoryNoDeleted(String value, int pageNo) {
        if(value == null || value.length() == 0){
            throw new ApiRequestException("Value is empty");
        }

        if(pageNo < 1){
            throw new ApiRequestException("Page must be more than zero");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<CategoryEntity> page = categoryRepository.findByNameContainingAndIsDeleted(
                                        value, false, pageable);

        return categoryPagingConvert.convert(pageNo, page);
    }

}
