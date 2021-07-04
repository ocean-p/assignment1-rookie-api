package com.daiduong.demo.controller;

import java.util.List;

import com.daiduong.demo.entity.CategoryEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.service.CategoryService;
import com.daiduong.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/category")
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/admin/category/new")
    public CategoryEntity addCategory(@RequestBody CategoryEntity entity) {
        return categoryService.addCategory(entity);
    }

    @PutMapping("/admin/category/new/{id}")
    public CategoryEntity updateCategory(@PathVariable("id") int id,
                                        @RequestBody CategoryEntity entity)
    {
        return categoryService.updateCategory(id, entity);
    }
    
    @DeleteMapping("/admin/category/deletion/{id}")
    public CategoryEntity deleteCategory(@PathVariable("id") int id){
        return categoryService.deleteCategory(id);
    }

    @PostMapping("/admin/product/new")
    public ProductEntity addProduct(@RequestBody ProductEntity entity){
        return productService.addProduct(entity);
    }
}
