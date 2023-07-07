package com.ecommercemicro.userservice.dto;

import com.ecommercemicro.userservice.data.entities.Address;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String mailAddress;
    private String name;
    private String lastName;
    private String phoneNumber;
    private List<Address> addresses;
}
