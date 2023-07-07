package com.ecommercemicro.orderservice.service;

import com.ecommercemicro.orderservice.client.inventory.InventoryClient;
import com.ecommercemicro.orderservice.client.user.UserClient;
import com.ecommercemicro.orderservice.client.user.UserResponse;
import com.ecommercemicro.orderservice.data.Order;
import com.ecommercemicro.orderservice.data.OrderLineItems;
import com.ecommercemicro.orderservice.data.OrderLineItemsRepository;
import com.ecommercemicro.orderservice.data.OrderRepository;
import com.ecommercemicro.orderservice.dto.OrderLineItemsDto;
import com.ecommercemicro.orderservice.dto.OrderRequest;
import com.ecommercemicro.orderservice.dto.SoldItems;
import com.ecommercemicro.orderservice.dto.SoldItemsRequest;
import com.ecommercemicro.orderservice.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode).toList();
        List<Integer> quantities = orderLineItems.stream()
                .map(OrderLineItems::getQuantity).toList();
        List<SoldItems> soldItemsList = new ArrayList<>();
        for (int i = 0; i < skuCodes.size(); i++){
            SoldItems soldItems = SoldItems.builder()
                    .skuCode(skuCodes.get(i))
                    .count(quantities.get(i)).build();
            soldItemsList.add(soldItems);
        }
        SoldItemsRequest soldItemsRequest = SoldItemsRequest.builder()
                .soldItems(soldItemsList).build();

        Order order = mapToDto(orderRequest);
        orderRepository.save(order);
        for (OrderLineItems lineItems : orderLineItems){
            lineItems.setOrder(order);
            orderLineItemsRepository.save(lineItems);
        }

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())){
            //ürününleri stoktan düşmek için envanter servisini çağırıyoruz.
            Integer result = inventoryClient.itemsSold(soldItemsRequest);
        } finally {
            inventoryServiceLookup.end();
        }

        UserResponse response = userClient.getUserById(order.getUserId()).getData();
        kafkaTemplate.send("notificationTopic", new OrderEvent(order.getOrderNo(), response.getMailAddress(), response.getName(), response.getLastName(), order.getStatus()));
        return ("Order placed successfully. ");
    }

    @Override
    public String changeStatus(Long id, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
            UserResponse response = userClient.getUserById(order.getUserId()).getData();
            kafkaTemplate.send("notificationTopic", new OrderEvent(order.getOrderNo(), response.getMailAddress(), response.getName(), response.getLastName(), order.getStatus()));
            return "Successfully completed";
        }
        return "Order not exists. ";
    }

    private static Order mapToDto(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        String address = orderRequest.getAddress().getAddressBody() + ", " + orderRequest.getAddress().getZipCode() + ", " +
                orderRequest.getAddress().getDistrict() + "/" + orderRequest.getAddress().getCity() + "/" + orderRequest.getAddress().getCountry();
        order.setAddress(address);
        order.setUserId(orderRequest.getUserId());
        order.setStatus("Sipariş Alındı");
        return order;
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
