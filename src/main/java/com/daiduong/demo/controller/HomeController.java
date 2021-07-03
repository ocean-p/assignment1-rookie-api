package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/admin/category/new")
    public CategoryEntity addCategory(@RequestBody CategoryEntity entity) {
        return categoryService.addCategory(entity);
    }
}
