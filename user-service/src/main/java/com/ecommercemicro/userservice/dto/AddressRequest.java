package com.ecommercemicro.userservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private Long userId;
    private String addressBody;
    private Integer zipCode;
    private String district;
    private String city;
    private String country;
}
