package com.ecommercemicro.orderservice.service;

import com.ecommercemicro.orderservice.dto.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

}
