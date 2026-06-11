package com.swiftorder.order_service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String customerName;
    private String product;
    private Integer quantity;
    private Double price;
}
