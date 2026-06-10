package com.swiftorder.orderservice.service;

import com.swiftorder.orderservice.dto.OrderEvent;
import com.swiftorder.orderservice.dto.OrderRequest;
import com.swiftorder.orderservice.entity.Order;
import com.swiftorder.orderservice.kafka.OrderProducer;
import com.swiftorder.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order placeOrder(OrderRequest request) {

        // 1. Build the Order entity from the request
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .product(request.getProduct())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        // 2. Save to MySQL — @PrePersist sets status + createdAt automatically
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved to DB with id: {}", savedOrder.getId());

        // 3. Build the Kafka event from saved order
        OrderEvent event = OrderEvent.builder()
                .orderId(savedOrder.getId())
                .customerName(savedOrder.getCustomerName())
                .product(savedOrder.getProduct())
                .quantity(savedOrder.getQuantity())
                .price(savedOrder.getPrice())
                .status(savedOrder.getStatus())
                .build();

        // 4. Publish event to Kafka
        orderProducer.sendOrderEvent(event);

        return savedOrder;
    }
}