package com.ecommercemicro.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String addressBody;
    private Integer zipCode;
    private String district;
    private String city;
    private String country;
}