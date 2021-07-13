package com.daiduong.demo.convert;

import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.entity.CartEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartConvert {
    
    @Autowired
    private ProductConvert productConvert;

    public CartDTO toDTO(CartEntity cartEntity){
        CartDTO cartDTO = new CartDTO();
        ProductDTO productDTO = productConvert.toDTO(cartEntity.getProduct());
        cartDTO.setProducts(productDTO);
        cartDTO.setQuantity(cartEntity.getQuantity());
        cartDTO.setUsername(cartEntity.getAccount().getUsername());
        return cartDTO;
    }
}
