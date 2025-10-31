package com.myapp.notification.config;

import com.myapp.orders.dto.Order;
import com.myapp.payment.dto.Payment;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaPaymentConsumerConfig {

    @Bean
    public ConsumerFactory<String, Payment> paymentConsumerFactory(@Value("${spring.kafka.bootstrap-servers}")  String bootstrapServers, JsonDeserializer<Payment> paymentDeserializer) {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG, "payment");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        final Deserializer<String> keyDeserializer = new StringDeserializer();
        return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, paymentDeserializer);
    }

    @Bean
    public JsonDeserializer<Payment> paymentDeserializer() {
        final JsonDeserializer<Payment> paymentDeserializer = new JsonDeserializer<>(Payment.class);
        paymentDeserializer.addTrustedPackages(Payment.class.getPackage().getName());

        return paymentDeserializer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Payment> paymentListenerContainerFactory(ConsumerFactory<String, Payment> paymentConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Payment> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentConsumerFactory);
        return factory;
    }
}
