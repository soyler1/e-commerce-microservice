package com.ecommercemicro.orderservice.client.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;
    private String addressBody;
    private Integer zipCode;
    private String district;
    private String city;
    private String country;
}
