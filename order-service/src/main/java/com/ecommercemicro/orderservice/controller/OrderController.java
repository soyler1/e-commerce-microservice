package com.ecommercemicro.orderservice.controller;

import com.ecommercemicro.orderservice.dto.BaseResponse;
import com.ecommercemicro.orderservice.dto.OrderRequest;
import com.ecommercemicro.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    //@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public BaseResponse<String> placeOrder(@RequestBody OrderRequest orderRequest){
        String result = orderService.placeOrder(orderRequest);
        return BaseResponse.<String>builder()
                .responseCode(1)
                .responseDesc("success")
                .data(result).build();
    }

    public BaseResponse<String> fallbackMethod(OrderRequest orderRequest, RuntimeException e){
        return BaseResponse.<String>builder()
                .responseCode(4)
                .responseDesc("cannot reach inventory service")
                .data("Something went wrong. Please try to create order again.").build();
    }

    @GetMapping("/changeStatus/{id}")
    public BaseResponse<String> changeStatus(@PathVariable("id") Long id, @RequestParam("status") String status){
        orderService.changeStatus(id, status);
        return BaseResponse.<String>builder()
                .responseCode(1)
                .responseDesc("success").build();
    }
}
