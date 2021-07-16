package com.daiduong.demo.convert;

import java.util.ArrayList;
import java.util.List;

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

    public List<ProductDTO> toDTOList(List<ProductEntity> entityList) {
        List<ProductDTO> dtoList = new ArrayList<>();
        for (ProductEntity productEntity : entityList) {
            ProductDTO dto = toDTO(productEntity);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
