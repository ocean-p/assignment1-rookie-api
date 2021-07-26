package com.daiduong.demo.service;

import java.util.List;
import java.util.Optional;

import com.daiduong.demo.convert.CartPagingConvert;
import com.daiduong.demo.dto.CartDTO;
import com.daiduong.demo.dto.CartPagingDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.CartEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.exception.ErrorCode;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.repository.CartRepository;
import com.daiduong.demo.repository.ProductRepository;
import com.daiduong.demo.service.interfaces.ICartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ErrorCode errorCode;

    @Autowired
    private CartPagingConvert cartPagingConvert;

    @Override
    public CartPagingDTO viewCart(int pageNo, String valueSort, CartDTO cartDTO) {
        String username = cartDTO.getUsername();
        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new ApiRequestException(errorCode.getACCOUNT_NOT_FOUND()));
        if(accountEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        Pageable pageable = PageRequest.of(pageNo - 1, 4, Sort.by(valueSort).descending());
        Page<CartEntity> page = cartRepository.findByAccount(accountEntity, pageable);
        List<CartEntity> list = cartRepository.findByAccount(accountEntity);
        
        int totalPrice = 0;
        int totalQuantity = 0;
        for (CartEntity cartEntity : list) {
            totalPrice += cartEntity.getProduct().getPrice() * cartEntity.getQuantity();
            totalQuantity += cartEntity.getQuantity();
        }

        CartPagingDTO result = cartPagingConvert.convert(pageNo, page);
        result.setTotalPrice(totalPrice);
        result.setTotalQuantity(totalQuantity);
        return result;
    }

    @Override
    public String addToCart(CartDTO cartDTO) {
        String username = cartDTO.getUsername();
        int productId = cartDTO.getProduct().getId();

        AccountEntity accountEntity = accountRepository.findById(username)
                .orElseThrow(() -> new ApiRequestException(errorCode.getACCOUNT_NOT_FOUND()));
        if(accountEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(errorCode.getPRODUCT_NOT_FOUND()));
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }        

        Optional<CartEntity> cartOption = cartRepository.findByAccountAndProduct(accountEntity, productEntity);
        if(cartOption.isPresent()){
            CartEntity cartEntity = cartOption.get();
            cartEntity.setQuantity(cartEntity.getQuantity() + 1);
            cartRepository.save(cartEntity);
        }
        else{
            CartEntity cartEntity = new CartEntity();
            if(cartRepository.count() < 1){
                cartEntity.setId(1);
            }
            else{
                cartEntity.setId(cartRepository.findMaxId() + 1);
            }
            cartEntity.setAccount(accountEntity);
            cartEntity.setProduct(productEntity);
            cartEntity.setQuantity(1);
            cartRepository.save(cartEntity);
        }

        return "Success to add product to cart";
    }
}
