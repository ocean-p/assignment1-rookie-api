package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.ProductDTO;

public interface IProductService {
    // add new product
    public ProductDTO addProduct(ProductDTO product);

    // get all products
    public List<ProductDTO> getAllProducts();

    // update products
    public ProductDTO updateProduct(int id, ProductDTO product);

    // delete product
    public ProductDTO deleteProduct(int id);
}
