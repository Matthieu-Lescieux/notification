package com.myapp.notification.consumer;

import com.myapp.payment.dto.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentConsumer {

    @KafkaListener(topics = "payments", groupId = "notification", containerFactory = "paymentListenerContainerFactory")
    public void listenPayment(Payment payment) {
        System.out.println("[NOTIFICATION] Received payment Message in group notification: " + payment.getPaymentId());
    }
}
