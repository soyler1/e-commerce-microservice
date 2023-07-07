package com.ecommercemicro.inventoryservice.service;

import com.ecommercemicro.inventoryservice.data.dto.InventoryResponse;
import com.ecommercemicro.inventoryservice.dto.SoldItemsRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> stockCount(List<String> skuCodes);
    Integer stockCount(String skuCode);
    Integer soldItems(SoldItemsRequest soldItemsRequest);
}
