package com.ecommercemicro.productservice.service;

import com.ecommercemicro.productservice.data.Product;
import com.ecommercemicro.productservice.data.ProductRepository;
import com.ecommercemicro.productservice.dto.ProductRequest;
import com.ecommercemicro.productservice.dto.ProductResponse;
import com.ecommercemicro.productservice.dto.ProductWithCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepository productRepository;
    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .productName(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public List<ProductResponse> getAll(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<Product> products = productRepository.findAll(pageable).getContent();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public ProductResponse getById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(this::mapToProductResponse).orElse(null);
    }

    @Override
    public List<ProductResponse> getByProductNameContains(String productName) {
        List<Product> products = productRepository.getByProductNameContains(productName);
        return products.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public List<ProductResponse> getByCategoryIdIn(List<Long> categories) {
        List<Product> products = productRepository.getByCategory_CategoryIdIn(categories);
        return products.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public List<ProductWithCategoryDto> getProductWithCategoryDetails() {
        return productRepository.getProductWithCategoryDetails();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
