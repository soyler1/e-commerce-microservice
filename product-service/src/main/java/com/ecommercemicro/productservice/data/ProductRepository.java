package com.ecommercemicro.productservice.data;

import com.ecommercemicro.productservice.dto.ProductWithCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getByProductNameContains(String productName);

    List<Product> getByCategory_CategoryIdIn(List<Long> categories);

    @Query(value = "Select new com.ecommercemicro.productservice.dto.ProductWithCategoryDto(p.id, p.productName, c.categoryName, p.description, p.price) From Product p Inner Join p.category c")
    List<ProductWithCategoryDto> getProductWithCategoryDetails();

}
