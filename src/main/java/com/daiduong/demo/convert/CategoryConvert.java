package com.daiduong.demo.convert;

import java.util.List;
import java.util.stream.Collectors;

import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.entity.CategoryEntity;

import org.springframework.stereotype.Component;

@Component
public class CategoryConvert {
    
    public CategoryEntity toEntity(CategoryDTO category){
        CategoryEntity entity = new CategoryEntity();
        entity.setName(category.getName());
        entity.setDescription(category.getDescription());
        return entity;
    }

    public CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreateDate(entity.getCreateDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setDeleted(entity.isDeleted());
        return dto;
    }

    public List<CategoryDTO> toDTOList(List<CategoryEntity> entityList) {
        List<CategoryDTO> dtoList = entityList.stream().map(entity -> toDTO(entity))
                                    .collect(Collectors.toList());
        
        return dtoList;
    }
}
