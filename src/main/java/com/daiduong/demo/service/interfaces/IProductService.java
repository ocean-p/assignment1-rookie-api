package com.daiduong.demo.service.interfaces;

import java.util.List;

import com.daiduong.demo.dto.HomePageCustomerDTO;
import com.daiduong.demo.dto.PagingProductDTO;
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

    // get list product no delete, quantity > 0
    public List<ProductDTO> getProductNoDeleteQuantityMoreZero();

    // get product by category
    public List<ProductDTO> getProductByCategory(int categoryId);

    // get product by id - details
    public ProductDTO getProductById(int productId);

    // home page for customer
    public HomePageCustomerDTO loadHomePageCustomer();

    public PagingProductDTO pagingProductNoDelete(int pageNo, int pageSize);
}
