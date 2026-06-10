package com.swiftorder.notificationservice.kafka;

import com.swiftorder.notificationservice.dto.OrderEvent;
import com.swiftorder.notificationservice.entity.Notification;
import com.swiftorder.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    // @KafkaListener tells Spring:
    // "Watch topic 'order-created', use group 'notification-group'"
    // Every time a message arrives → call this method automatically
    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group"
    )
    public void consumeOrderEvent(OrderEvent event) {

        log.info("─────────────────────────────────────────");
        log.info("Kafka event received!");
        log.info("Order ID   : {}", event.getOrderId());
        log.info("Customer   : {}", event.getCustomerName());
        log.info("Product    : {}", event.getProduct());
        log.info("Quantity   : {}", event.getQuantity());
        log.info("Price      : ₹{}", event.getPrice());
        log.info("Status     : {}", event.getStatus());
        log.info("─────────────────────────────────────────");

        // Build a human-readable notification message
        String message = String.format(
                "Dear %s, your order for %d x %s worth ₹%.2f has been %s successfully!",
                event.getCustomerName(),
                event.getQuantity(),
                event.getProduct(),
                event.getPrice(),
                event.getStatus()
        );

        // Build and save Notification to notificationdb
        Notification notification = Notification.builder()
                .orderId(event.getOrderId())
                .customerName(event.getCustomerName())
                .product(event.getProduct())
                .quantity(event.getQuantity())
                .price(event.getPrice())
                .status(event.getStatus())
                .message(message)
                .build();

        notificationRepository.save(notification);

        log.info("Notification saved to DB: {}", message);
    }
}