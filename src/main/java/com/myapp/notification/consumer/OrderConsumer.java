package com.myapp.notification.consumer;

import com.myapp.orders.dto.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderConsumer {

    @KafkaListener(topics = "order-created", groupId = "notification", containerFactory = "orderListenerContainerFactory")
    public void listenOrderCreated(Order order) {
        System.out.println("[NOTIFICATION] Received order Message in group notification: " + order.getOrderId());
    }
}
