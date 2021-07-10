package com.daiduong.demo.convert;

import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.entity.ProductEntity;

import org.springframework.stereotype.Component;

@Component
public class ProductConvert {
    
    public ProductEntity toEntity(ProductDTO product){
        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setImage(product.getImage());
        entity.setQuantity(product.getQuantity());
        entity.setDescription(product.getDescription());
        return entity;
    }

    public ProductDTO toDTO(ProductEntity entity){
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setImage(entity.getImage());
        dto.setQuantity(entity.getQuantity());
        dto.setDescription(entity.getDescription());
        dto.setAverageRate(entity.getAverageRate());
        dto.setCreateDate(entity.getCreateDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setDeleted(entity.isDeleted());
        return dto;
    }
}
