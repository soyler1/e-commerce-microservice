package com.ecommercemicro.orderservice.client.user;

import com.ecommercemicro.orderservice.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    BaseResponse<UserResponse> getUserById(@PathVariable("id") Long id);
}
