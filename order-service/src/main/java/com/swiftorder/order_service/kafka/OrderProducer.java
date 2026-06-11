package com.swiftorder.order_service.kafka;

import com.swiftorder.order_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "order-created";

    public void sendOrderEvent(OrderEvent event) {
        log.info("Sending Kafka event to topic [{}] → {}", TOPIC, event);
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event);
        log.info("Kafka event sent successfully for orderId: {}", event.getOrderId());
    }
}
