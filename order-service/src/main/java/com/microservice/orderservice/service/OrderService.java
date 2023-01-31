package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderLineItemsDTO;
import com.microservice.orderservice.dto.OrderRequestDTO;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.entity.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDTO orderRequestDTO){
       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineItems> orderLineItems = orderRequestDTO.getOrderLineItemsDTOList()
               .stream()
               .map(this::mapToDto)
               .collect(Collectors.toList());

       order.setOrderLineItems(orderLineItems);

       orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());

        return orderLineItems;
    }
}
