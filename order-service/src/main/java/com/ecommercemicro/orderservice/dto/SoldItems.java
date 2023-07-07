package com.ecommercemicro.orderservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SoldItems {
    private String skuCode;
    private Integer count;
}
