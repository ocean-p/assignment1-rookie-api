package com.daiduong.demo.convert;

import java.util.List;

import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;
import com.daiduong.demo.entity.ProductEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductPagingConvert {
    
    @Autowired
    private ProductConvert productConvert;

    public ProductPagingDTO convert(int pageNo, Page<ProductEntity> page){
        ProductPagingDTO result = new ProductPagingDTO();
        result.setCurrentPage(pageNo);
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());

        List<ProductDTO> list = productConvert.toDTOList(page.getContent());
        result.setProductList(list);
        return result;
    }
}
