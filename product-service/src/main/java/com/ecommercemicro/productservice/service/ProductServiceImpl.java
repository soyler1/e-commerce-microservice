package com.ecommercemicro.productservice.service;

import com.ecommercemicro.productservice.data.Product;
import com.ecommercemicro.productservice.data.ProductRepository;
import com.ecommercemicro.productservice.dto.ProductRequest;
import com.ecommercemicro.productservice.dto.ProductResponse;
import com.ecommercemicro.productservice.dto.ProductWithCategoryDto;
import com.ecommercemicro.productservice.inventory.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
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

        return mapProductResponsesWithQuantityInStock(products);
    }

    @Override
    public List<ProductResponse> getAll(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<Product> products = productRepository.findAll(pageable).getContent();

        return mapProductResponsesWithQuantityInStock(products);
    }

    @Override
    public ProductResponse getById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            List<String> skuCodes = new ArrayList<>();
            skuCodes.add(product.getSkuCode());
            InventoryResponse[] inventoryResponses = callInventory(skuCodes);

            ProductResponse productResponse = mapToProductResponse(product);
            if (productResponse.getSkuCode().equals(inventoryResponses[0].getSkuCode()))
                productResponse.setQuantityInStock(inventoryResponses[0].getQuantity());

            return productResponse;
        }
        return null;
    }

    @Override
    public List<ProductResponse> getByProductNameContains(String productName) {
        List<Product> products = productRepository.getByProductNameContains(productName);
        return mapProductResponsesWithQuantityInStock(products);
    }

    @Override
    public List<ProductResponse> getByCategoryIdIn(List<Long> categories) {
        List<Product> products = productRepository.getByCategory_CategoryIdIn(categories);
        return mapProductResponsesWithQuantityInStock(products);
    }

    private List<ProductResponse> mapProductResponsesWithQuantityInStock(List<Product> products) {
        List<String> skuCodes = products.stream().map(Product::getSkuCode).toList();
        InventoryResponse[] inventoryResponses = callInventory(skuCodes);

        List<ProductResponse> productResponses = products.stream().map(this::mapToProductResponse).toList();
        for (ProductResponse productResponse : productResponses){
            for (InventoryResponse inventoryResponse : inventoryResponses){
                if (productResponse.getSkuCode().equals(inventoryResponse.getSkuCode()))
                    productResponse.setQuantityInStock(inventoryResponse.getQuantity());
            }
        }
        return productResponses;
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

    private InventoryResponse[] callInventory(List<String> skuCodes){
        String uri = "http://inventory-service/api/inventory";

        Span inventortyServiceCall = tracer.nextSpan().name("InventoryServiceCall");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventortyServiceCall.start())){
            return webClientBuilder.build().get()
                    .uri(uri, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
        }finally {
            inventortyServiceCall.end();
        }
    }
}
