package com.ecommercemicro.orderservice.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SoldItemsRequest {
    private List<SoldItems> soldItems;
}
