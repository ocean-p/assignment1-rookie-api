package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.entity.ProductEntity;

public interface IProductService {
    // add new product
    public ProductEntity addProduct(ProductEntity product);

    // get all products
    public List<ProductEntity> getAllProducts();

    // update products
    public ProductEntity updateProduct(int id, ProductEntity product);

    // delete product
    public ProductEntity deleteProduct(int id);
}
