package com.swiftorder.orderservice.kafka;

import com.swiftorder.orderservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor   // Lombok: injects KafkaTemplate via constructor
@Slf4j                     // Lombok: gives us log.info(), log.error() etc.
public class OrderProducer {

    // KafkaTemplate is Spring's class for sending messages to Kafka
    // <String, OrderEvent> means: key=String, value=OrderEvent
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String TOPIC = "order-created";   // Topic name

    public void sendOrderEvent(OrderEvent event) {
        log.info("Sending Kafka event to topic [{}] → {}", TOPIC, event);

        // send(topic, key, value)
        // key = orderId as string → Kafka uses this for partitioning
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event);

        log.info("Kafka event sent successfully for orderId: {}", event.getOrderId());
    }
}