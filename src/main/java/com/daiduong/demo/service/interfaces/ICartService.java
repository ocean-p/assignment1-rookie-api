package com.daiduong.demo.service.interfaces;

import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.dto.CartPagingDTO;

public interface ICartService {
    
    public CartPagingDTO viewCart(int pageNo, String valueSort, CartDTO cartDTO);

    public String addToCart(CartDTO cartDTO);
}
