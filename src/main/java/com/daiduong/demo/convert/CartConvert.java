package com.daiduong.demo.convert;

import java.util.List;
import java.util.stream.Collectors;

import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.entity.CartEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartConvert {
    
    @Autowired
    private ProductConvert productConvert;

    public CartDTO toDTO(CartEntity entity){
        CartDTO dto = new CartDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getAccount().getUsername());
        dto.setProduct(productConvert.toDTO(entity.getProduct()));
        dto.setQuantity(entity.getQuantity());
        return dto;
    }

    public List<CartDTO> toDTOList(List<CartEntity> entityList){
        List<CartDTO> dtoList = entityList.stream()
                .map(entity -> toDTO(entity))
                .collect(Collectors.toList());
        return dtoList;        
    }
}
