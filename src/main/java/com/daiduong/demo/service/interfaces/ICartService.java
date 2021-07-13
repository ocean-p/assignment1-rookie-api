package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.CartDTO;

public interface ICartService {
    
    public CartDTO addtoCart(String username, int productId, int quantity);

    public List<CartDTO> viewCart(String username);

    public List<CartDTO> deleteItemInCart(String username, int productId);

    public List<CartDTO> updateQuantityInCart(String username, int productId, int quantity);
}
