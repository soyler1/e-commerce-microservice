package com.ecommercemicro.productservice.service;

import com.ecommercemicro.productservice.dto.ProductRequest;
import com.ecommercemicro.productservice.dto.ProductResponse;
import com.ecommercemicro.productservice.dto.ProductWithCategoryDto;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAll();
    List<ProductResponse> getAll(int pageNo, int pageSize);
    ProductResponse getById(Long id);
    List<ProductResponse> getByProductNameContains(String productName);
    List<ProductResponse> getByCategoryIdIn(List<Long> categories);
    List<ProductWithCategoryDto> getProductWithCategoryDetails();
    void deleteById(Long id);

}
