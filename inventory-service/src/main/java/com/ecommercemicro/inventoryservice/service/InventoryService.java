package com.ecommercemicro.inventoryservice.service;

import com.ecommercemicro.inventoryservice.data.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> stockCount(List<String> skuCodes);
}
