package com.daiduong.demo.service.interfaces;

import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;

public interface IProductService {
    
    public ProductDTO addProduct(ProductDTO product);

    public ProductDTO updateProduct(int id, ProductDTO product);

    public String deleteProduct(int id);

    public String restoreProduct(int id);

    public ProductPagingDTO getAllProductsNoDelete(int pageNo);

    public ProductPagingDTO getAllProductsDeleted(int pageNo);

    public ProductPagingDTO searchProductNoDeleteByName(String value, int pageNo);

    public ProductDTO getProductById(int id);

    public ProductPagingDTO getProductNoDeleteByCategory(int categoryId, int pageNo);
}
