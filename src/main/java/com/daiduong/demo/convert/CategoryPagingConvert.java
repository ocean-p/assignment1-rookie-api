package com.daiduong.demo.convert;

import java.util.List;

import com.daiduong.demo.dto.CategoryDTO;
import com.daiduong.demo.dto.CategoryPagingDTO;
import com.daiduong.demo.entity.CategoryEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CategoryPagingConvert {

    @Autowired
    private CategoryConvert categoryConvert;
    
    public CategoryPagingDTO convert(int pageNo, Page<CategoryEntity> page){
        CategoryPagingDTO result = new CategoryPagingDTO();
        result.setCurrentPage(pageNo);
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());

        List<CategoryDTO> list = categoryConvert.toDTOList(page.getContent());
        result.setCategories(list);

        return result;
    }
}
