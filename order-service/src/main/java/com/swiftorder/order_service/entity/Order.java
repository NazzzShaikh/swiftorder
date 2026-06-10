package com.swiftorder.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")           // Table name in MySQL
@Data                             // Lombok: generates getters, setters, toString
@Builder                          // Lombok: enables Order.builder().field(val).build()
@NoArgsConstructor                // Lombok: generates no-arg constructor
@AllArgsConstructor               // Lombok: generates all-arg constructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto increment ID
    private Long id;

    private String customerName;
    private String product;
    private Integer quantity;
    private Double price;

    private String status;         // e.g. "PLACED", "CANCELLED"

    private LocalDateTime createdAt;

    @PrePersist                    // Runs automatically before saving to DB
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = "PLACED";
    }
}