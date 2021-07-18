package com.daiduong.demo.service.interfaces;

import com.daiduong.demo.dto.ProductDTO;
import com.daiduong.demo.dto.ProductPagingDTO;

public interface IProductService {
    
    public ProductDTO addProduct(ProductDTO product);

    public ProductDTO updateProduct(int id, ProductDTO product);

    public String deleteProduct(int id);

    public String restoreProduct(int id);

    public ProductPagingDTO getAllProductsNoDelete(int pageNo, String valueSort);

    public ProductPagingDTO getAllProductsDeleted(int pageNo, String valueSort);

    public ProductPagingDTO searchProductNoDeleteByName(String value, int pageNo, String valueSort);

    public ProductDTO getProductById(int id);

    public ProductPagingDTO getProductNoDeleteByCategory(int categoryId, int pageNo, String valueSort);

}
