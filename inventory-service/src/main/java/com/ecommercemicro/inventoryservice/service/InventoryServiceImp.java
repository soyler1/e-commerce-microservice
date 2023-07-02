package com.ecommercemicro.inventoryservice.service;

import com.ecommercemicro.inventoryservice.data.InventoryRepository;
import com.ecommercemicro.inventoryservice.data.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp implements InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<InventoryResponse> stockCount(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .quantity(inventory.getQuantity())
                                .build()
                ).toList();
    }
}
