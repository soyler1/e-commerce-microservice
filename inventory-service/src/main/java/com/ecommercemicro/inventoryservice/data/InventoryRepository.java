package com.ecommercemicro.inventoryservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCode(String skuCode);

    List<Inventory> findBySkuCodeIn(List<String> skuCodes);

    @Query(value = "Select i.quantity From Inventory i Where i.skuCode =:skuCode ")
    Integer stockCount(String skuCode);
}
