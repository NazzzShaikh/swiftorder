package com.swiftorder.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// This is the event payload published to Kafka topic "order-created"
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String customerName;
    private String product;
    private Integer quantity;
    private Double price;
    private String status;
}