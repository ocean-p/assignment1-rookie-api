package com.daiduong.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.daiduong.demo.convert.CategoryConvert;
import com.daiduong.demo.convert.CategoryPagingConvert;
import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;
import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.exception.ErrorCode;
import com.daiduong.demo.repository.CategoryRepository;
import com.daiduong.demo.repository.ProductRepository;
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
    private ErrorCode errorCode;

    @Autowired
    private CategoryConvert categoryConvert;

    @Autowired
    private CategoryPagingConvert categoryPagingConvert;

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public CategoryDTO getCategoryById(int id){
        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(
            () -> new ApiRequestException(errorCode.getCATEGORY_NOT_FOUND())
        );
        if(entity.isDeleted()){
            throw new ApiRequestException(errorCode.getCATEGORY_IS_DISABLED());
        }
        return categoryConvert.toDTO(entity);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO category){
        String name = category.getName();
        if(name == null || name.trim().length() == 0){
            throw new ApiRequestException(errorCode.getNAME_IS_EMPTY());
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
                errorCode.getCATEGORY_NOT_FOUND()
            ));

        if(categoryEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getCATEGORY_IS_DISABLED());
        }
            
        String newName = category.getName();
        String newDes = category.getDescription();

        if(newName == null || newName.trim().length() == 0)
        {
            throw new ApiRequestException(errorCode.getNAME_IS_EMPTY());
        }    

        if(newDes == null || newDes.trim().length() == 0)
        {
            throw new ApiRequestException(errorCode.getDESCRIPTION_IS_EMPTY());
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
                errorCode.getCATEGORY_NOT_FOUND()
            ));
        if(categoryEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getCATEGORY_IS_DISABLED());
        }

        List<ProductEntity> productEntityList = productRepository.findByCategoryAndIsDeleted(
                                                categoryEntity, false);
        for (ProductEntity productEntity : productEntityList) {
            productEntity.setDeleted(true);
            productEntity.setUpdateDate(LocalDate.now());
            productRepository.save(productEntity);
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
                errorCode.getCATEGORY_NOT_FOUND()
            ));
        if(categoryEntity.isDeleted() == false){
            throw new ApiRequestException(errorCode.getCATEGORY_ACTIVE());
        }

        categoryEntity.setDeleted(false);
        categoryEntity.setUpdateDate(LocalDate.now());
        categoryEntity = categoryRepository.save(categoryEntity);

        return "Restore Successfully!";
    }

    @Override
    public CategoryPagingDTO getAllCategoriesNoDelete(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<CategoryEntity> page = categoryRepository.findByIsDeleted(false, pageable);

        return categoryPagingConvert.convert(pageNo, page);
    }

    @Override
    public CategoryPagingDTO getAllCategoriesDeleted(int pageNo) {
        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<CategoryEntity> page = categoryRepository.findByIsDeleted(true, pageable);

        return categoryPagingConvert.convert(pageNo, page);
    }

    @Override
    public CategoryPagingDTO searchCategoryNoDeleted(String value, int pageNo) {
        if(value == null || value.trim().length() == 0){
            throw new ApiRequestException(errorCode.getSEARCH_VALUE_IS_EMPTY());
        }

        if(pageNo < 1){
            throw new ApiRequestException(errorCode.getPAGE_LESS_THAN_ONE());
        }
        Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by("updateDate").descending());
        Page<CategoryEntity> page = categoryRepository.findByNameContainingAndIsDeleted(
                                        value, false, pageable);

        return categoryPagingConvert.convert(pageNo, page);
    }

    @Override
    public List<CategoryDTO> getCategoryMenu() {
        List<CategoryEntity> list = categoryRepository.findByIsDeletedOrderByCreateDateDesc(false);
        return categoryConvert.toDTOList(list);
    }
    
}
