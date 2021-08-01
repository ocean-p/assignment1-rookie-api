package com.daiduong.demo.convert;

import java.util.List;
import java.util.stream.Collectors;

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
        entity.setImage2(product.getImage2());
        entity.setImage3(product.getImage3());
        entity.setImage4(product.getImage4());
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
        dto.setImage2(entity.getImage2());
        dto.setImage3(entity.getImage3());
        dto.setImage4(entity.getImage4());
        return dto;
    }

    public List<ProductDTO> toDTOList(List<ProductEntity> entityList) {
        List<ProductDTO> dtoList = entityList.stream().map(entity -> toDTO(entity))
                                   .collect(Collectors.toList());
        
        return dtoList;
    }
}
