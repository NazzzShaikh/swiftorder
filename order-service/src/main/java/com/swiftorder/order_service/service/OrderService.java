package com.swiftorder.order_service.service;

import com.swiftorder.order_service.dto.OrderEvent;
import com.swiftorder.order_service.dto.OrderRequest;
import com.swiftorder.order_service.entity.Order;
import com.swiftorder.order_service.kafka.OrderProducer;
import com.swiftorder.order_service.repository.OrderRepository;
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
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .product(request.getProduct())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order saved to DB with id: {}", savedOrder.getId());

        OrderEvent event = OrderEvent.builder()
                .orderId(savedOrder.getId())
                .customerName(savedOrder.getCustomerName())
                .product(savedOrder.getProduct())
                .quantity(savedOrder.getQuantity())
                .price(savedOrder.getPrice())
                .status(savedOrder.getStatus())
                .build();

        orderProducer.sendOrderEvent(event);
        return savedOrder;
    }
}
