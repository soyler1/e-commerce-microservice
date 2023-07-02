package com.ecommercemicro.orderservice.service;

import com.ecommercemicro.orderservice.client.inventory.InventoryResponse;
import com.ecommercemicro.orderservice.data.Order;
import com.ecommercemicro.orderservice.data.OrderLineItems;
import com.ecommercemicro.orderservice.data.OrderLineItemsRepository;
import com.ecommercemicro.orderservice.data.OrderRepository;
import com.ecommercemicro.orderservice.dto.OrderLineItemsDto;
import com.ecommercemicro.orderservice.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient webClient;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        boolean allItemsAreInStock = true;
        List<String> noStockItems = new ArrayList<>();

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode).toList();
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        orderRepository.save(order);

        //ürünün stokta olup olmadığını öğrenmek için inventory service'i çağırıyoruz.

        String uri = "http://localhost:8082/api/inventory";
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri(uri, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        for (OrderLineItems lineItems : orderLineItems) {

            for (InventoryResponse inventoryResponse : inventoryResponses){
                if (lineItems.getSkuCode().equals(inventoryResponse.getSkuCode())){
                    if (lineItems.getQuantity() > inventoryResponse.getQuantity()){
                        allItemsAreInStock = false;
                        noStockItems.add(lineItems.getSkuCode());
                        log.info("Item '{} is not in stock'", lineItems.getSkuCode());
                    }
                    else{
                        lineItems.setOrder(order);
                        orderLineItemsRepository.save(lineItems);
                    }
                }
            }
        }
        StringBuilder result = new StringBuilder("Order placed successfully. ");
        if (!allItemsAreInStock)
            result.append("\n Due to insufficient stock, following products could not be included to order: ");
        for (String product : noStockItems) {
            result.append("\n ").append(product).append(" ");
        }
        return (result.toString());
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
