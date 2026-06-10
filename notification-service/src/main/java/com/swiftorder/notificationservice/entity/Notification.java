package com.swiftorder.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;           // Which order triggered this notification
    private String customerName;
    private String product;
    private Integer quantity;
    private Double price;
    private String status;          // "PLACED" etc.

    private String message;         // Human readable message we generate

    private LocalDateTime receivedAt;   // When we received the Kafka event

    @PrePersist
    public void prePersist() {
        this.receivedAt = LocalDateTime.now();
    }
}