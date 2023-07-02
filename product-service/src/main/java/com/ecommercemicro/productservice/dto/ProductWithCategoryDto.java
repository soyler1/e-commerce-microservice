package com.ecommercemicro.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithCategoryDto {
    private Long id;
    private String productName;
    private String categoryName;
    private String description;
    private BigDecimal price;
}
