package com.ecommercemicro.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long userId;
    private Address address;
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
