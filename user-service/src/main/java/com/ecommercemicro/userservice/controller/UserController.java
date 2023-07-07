package com.ecommercemicro.userservice.controller;

import com.ecommercemicro.userservice.dto.AddressRequest;
import com.ecommercemicro.userservice.dto.BaseResponse;
import com.ecommercemicro.userservice.dto.UserCreateRequest;
import com.ecommercemicro.userservice.dto.UserResponse;
import com.ecommercemicro.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<String> createUser(@RequestBody UserCreateRequest userCreateRequest){
        String result = userService.createUser(userCreateRequest);
        return BaseResponse.<String>builder()
                .responseCode(1)
                .responseDesc("success")
                .data(result).build();
    }

    @GetMapping("/{id}")
    public BaseResponse<UserResponse> getUserById(@PathVariable("id") Long id){
        UserResponse response = userService.findUserById(id);

        if (response!=null) return BaseResponse.<UserResponse>builder()
                .responseCode(1)
                .responseDesc("success")
                .data(response).build();

        return BaseResponse.<UserResponse>builder()
                .responseCode(2)
                .responseDesc("not found").build();
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BaseResponse<Long> login(@RequestParam("email") String email, @RequestParam("password") String password){
        Long result = userService.login(email, password);
        if(result != -1) return BaseResponse.<Long>builder()
                .responseCode(1)
                .responseDesc("success")
                .data(result).build();

        return BaseResponse.<Long>builder()
                .responseCode(2)
                .responseDesc("wrong credentials")
                .data(result).build();
    }

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<String> addAddress(@RequestBody AddressRequest addressRequest){
        String result = userService.addAddress(addressRequest);
        return BaseResponse.<String>builder()
                .responseCode(1)
                .responseDesc("success")
                .data(result).build();
    }
}
