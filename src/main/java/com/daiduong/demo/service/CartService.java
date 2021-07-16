package com.daiduong.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.daiduong.demo.convert.CartConvert;
import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.CartEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.repository.CartRepository;
import com.daiduong.demo.repository.ProductRepository;
import com.daiduong.demo.service.interfaces.ICartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartConvert cartConvert;

    public int findIndex(int productId, List<CartEntity> cartEntityList) {
        for (int i = 0; i < cartEntityList.size(); i++) {
            if (cartEntityList.get(i).getProduct().getId() == productId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public CartDTO addtoCart(String username, int productId, int quantity) {
        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new ApiRequestException("Username not found"));
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException("Product not found"));
        if (productEntity.isDeleted()) {
            throw new ApiRequestException("Product was deleted");
        }

        if (quantity <= 0) {
            throw new ApiRequestException("Quantity must be greater than zero");
        }

        if (quantity > productEntity.getQuantity()) {
            throw new ApiRequestException("Quantity is greater than available");
        }

        CartDTO cartDTO;
        List<CartEntity> cartEntityList = cartRepository.findByAccount(accountEntity);
        int index = findIndex(productId, cartEntityList);
        if (index < 0) {
            int id;
            if (cartRepository.count() < 1) {
                id = 1;
            } else {
                id = cartRepository.findMaxId() + 1;
            }
            CartEntity cartEntity = new CartEntity(id, accountEntity, productEntity, quantity);
            cartEntity = cartRepository.save(cartEntity);
            cartDTO = cartConvert.toDTO(cartEntity);
        } 
        else {
            CartEntity cartEntity = cartEntityList.get(index);
            int currentQuantity = cartEntity.getQuantity();
            if (quantity + currentQuantity > productEntity.getQuantity()) {
                throw new ApiRequestException("Quantity is greater than available");
            }

            cartEntity.setQuantity(currentQuantity + quantity);
            cartEntity = cartRepository.save(cartEntity);
            cartDTO = cartConvert.toDTO(cartEntity);
        }
        return cartDTO;
    }

    @Override
    public List<CartDTO> viewCart(String username) {
        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new ApiRequestException("Username not found"));
        List<CartDTO> cartDTOList = new ArrayList<>();
        List<CartEntity> cartEntityList = cartRepository.findByAccount(accountEntity);
        for (CartEntity cartEntity : cartEntityList) {
            CartDTO cartDTO = cartConvert.toDTO(cartEntity);
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

    @Override
    public List<CartDTO> deleteItemInCart(String username, int productId) {
        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new ApiRequestException("Username not found"));
        List<CartEntity> cartEntityList = cartRepository.findByAccount(accountEntity);
        int index = findIndex(productId, cartEntityList);
        if (index < 0) {
            throw new ApiRequestException("Not found this product in cart");
        }
        int cartId = cartEntityList.get(index).getId();
        cartRepository.deleteById(cartId);

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartEntityList = cartRepository.findByAccount(accountEntity);
        for (CartEntity cartEntity : cartEntityList) {
            CartDTO cartDTO = cartConvert.toDTO(cartEntity);
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

    @Override
    public List<CartDTO> updateQuantityInCart(String username, int productId, int quantity) {
        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new ApiRequestException("Username not found"));

        if (quantity <= 0) {
            throw new ApiRequestException("Quantity must be greater than zero");
        }

        List<CartEntity> cartEntityList = cartRepository.findByAccount(accountEntity);
        int index = findIndex(productId, cartEntityList);
        if (index < 0) {
            throw new ApiRequestException("Not found this product in cart");
        }

        CartEntity cartEntity = cartEntityList.get(index);
        if (quantity > cartEntity.getProduct().getQuantity()) {
            throw new ApiRequestException("Quantity is more than available");
        }

        cartEntity.setQuantity(quantity);
        cartRepository.save(cartEntity);

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartEntityList = cartRepository.findByAccount(accountEntity);
        for (CartEntity ce : cartEntityList) {
            CartDTO cartDTO = cartConvert.toDTO(ce);
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

}
