package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.InventoryResponseDTO;
import com.microservice.orderservice.dto.OrderLineItemsDTO;
import com.microservice.orderservice.dto.OrderRequestDTO;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.entity.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequestDTO orderRequestDTO){
       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineItems> orderLineItems = orderRequestDTO.getOrderLineItemsDTOList()
               .stream()
               .map(this::mapToDto)
               .collect(Collectors.toList());

       order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        /*Call inventoryService, and place order if is in stock*/
        InventoryResponseDTO[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponseDTO::getIsInStock);

        if(allProductsInStock) {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock, Plase try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());

        return orderLineItems;
    }
}
