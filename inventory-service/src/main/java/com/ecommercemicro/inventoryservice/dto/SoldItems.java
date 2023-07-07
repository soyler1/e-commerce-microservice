package com.ecommercemicro.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SoldItems {
    private String skuCode;
    private Integer count;
}
