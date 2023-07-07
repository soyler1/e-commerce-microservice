package com.ecommercemicro.userservice.service;

import com.ecommercemicro.userservice.dto.AddressRequest;
import com.ecommercemicro.userservice.dto.UserCreateRequest;
import com.ecommercemicro.userservice.dto.UserResponse;

public interface UserService {
    String createUser(UserCreateRequest serviceInput);
    Long login(String email, String password);
    String addAddress(AddressRequest addressRequest);
    UserResponse findUserById(Long id);

}
