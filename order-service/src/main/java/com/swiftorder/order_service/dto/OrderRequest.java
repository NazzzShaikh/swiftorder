package com.swiftorder.orderservice.dto;

import lombok.Data;

// This is what the user sends in the POST /api/orders body
@Data
public class OrderRequest {
    private String customerName;
    private String product;
    private Integer quantity;
    private Double price;
}