package com.ecommercemicro.orderservice.controller;

import com.ecommercemicro.orderservice.dto.BaseResponse;
import com.ecommercemicro.orderservice.dto.OrderRequest;
import com.ecommercemicro.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<String> placeOrder(@RequestBody OrderRequest orderRequest){
        String result = orderService.placeOrder(orderRequest);
        return BaseResponse.<String>builder()
                .responseCode(1)
                .responseDesc("success")
                .data(result).build();
    }
}
