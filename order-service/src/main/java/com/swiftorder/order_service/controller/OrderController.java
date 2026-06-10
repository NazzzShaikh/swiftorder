package com.swiftorder.orderservice.controller;

import com.swiftorder.orderservice.dto.OrderRequest;
import com.swiftorder.orderservice.entity.Order;
import com.swiftorder.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // POST /api/orders
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        log.info("Received order request for customer: {}", request.getCustomerName());
        Order order = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // GET /api/orders/health  — simple check to verify service is up
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("order-service is UP ✅");
    }
}