package com.ecommercemicro.inventoryservice.service;

import com.ecommercemicro.inventoryservice.data.Inventory;
import com.ecommercemicro.inventoryservice.data.InventoryRepository;
import com.ecommercemicro.inventoryservice.data.dto.InventoryResponse;
import com.ecommercemicro.inventoryservice.dto.SoldItems;
import com.ecommercemicro.inventoryservice.dto.SoldItemsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public Integer stockCount(String skuCode) {
        Optional<Inventory> optionalInventory = inventoryRepository.findBySkuCode(skuCode);
        if (optionalInventory.isPresent())
            return optionalInventory.get().getQuantity();
        return -1;
    }

    @Override
    public Integer soldItems(SoldItemsRequest soldItemsRequest) {
        for (SoldItems soldItems : soldItemsRequest.getSoldItems()){
            Optional<Inventory> optionalInventory = inventoryRepository.findBySkuCode(soldItems.getSkuCode());
            if (optionalInventory.isPresent()){
                Inventory inventory = optionalInventory.get();
                log.warn("stock: " + inventory.getQuantity() + " quantity: " + soldItems.getCount());
                inventory.setQuantity(inventory.getQuantity() - soldItems.getCount());
                inventoryRepository.save(inventory);
            }
            else
                log.warn("Sku code {} not found in inventory service!", soldItems.getSkuCode());
        }
        return 5;
    }
}
