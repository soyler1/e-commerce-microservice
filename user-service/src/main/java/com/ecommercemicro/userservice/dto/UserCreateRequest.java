package com.ecommercemicro.userservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String mailAddress;
    private String password;
    private String name;
    private String lastName;
    private String phoneNumber;
}
