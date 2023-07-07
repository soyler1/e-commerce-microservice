package com.ecommercemicro.inventoryservice.controller;

import com.ecommercemicro.inventoryservice.data.dto.InventoryResponse;
import com.ecommercemicro.inventoryservice.dto.SoldItemsRequest;
import com.ecommercemicro.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> stockCount(@RequestParam List<String> skuCodes) {
        return inventoryService.stockCount(skuCodes);
    }

    @GetMapping("/{skuCode}")
    public Integer stockCount(@PathVariable("skuCode") String skuCode){
        return inventoryService.stockCount(skuCode);
    }

    @PostMapping("/itemsSold")
    public Integer itemsSold(@RequestBody SoldItemsRequest soldItemsRequest){
        return inventoryService.soldItems(soldItemsRequest);
    }
}
