package com.daiduong.demo.convert;

import java.util.List;

import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.dto.CartPagingDTO;
import com.daiduong.demo.entity.CartEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CartPagingConvert {
    
    @Autowired
    private CartConvert cartConvert;

    public CartPagingDTO convert(int pageNo, Page<CartEntity> page){
        CartPagingDTO result = new CartPagingDTO();
        result.setCurrentPage(pageNo);
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());

        List<CartDTO> list = cartConvert.toDTOList(page.getContent());
        result.setCart(list);
        return result;
    }
}
