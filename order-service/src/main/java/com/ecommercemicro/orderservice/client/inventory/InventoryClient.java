package com.ecommercemicro.orderservice.client.inventory;

import com.ecommercemicro.orderservice.dto.SoldItemsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/api/inventory/itemsSold")
    Integer itemsSold(@RequestBody SoldItemsRequest soldItemsRequest);
}
