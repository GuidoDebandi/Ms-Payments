package com.example.demo.config.rabbit;


import com.example.demo.model.PaymentOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentTopicProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentTopicProducer.class);

    @Value("${rabbitmq.payment.done.queue.name}")
    private String paymentDoneQueue;
    @Value("${rabbitmq.payment.done.exchange.name}")
    private String paymentDoneExchange;
    @Value("${rabbitmq.payment.done.key}")
    private String paymentDoneBinding;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage (PaymentOrder paymentDoneMessage) {


        try {
            Map<String, Object> messageMap = getStringMessage(paymentDoneMessage);
            String message = objectMapper.writeValueAsString(messageMap);

            rabbitTemplate.convertAndSend(paymentDoneExchange, paymentDoneBinding, message);
            LOGGER.info("Mensaje enviado al topic 'payment_topic'");
        } catch (Exception e) {
            LOGGER.error("Error al enviar un mensaje a la cola de RabbitMQ, mensaje: {} ", e.getMessage());
        }
    }

    private static Map<String, Object> getStringMessage(PaymentOrder paymentDoneMessage) {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "payment-done");
        messageMap.put("exchange", "payment_topic");
        messageMap.put("queue", "payment_topic");

        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("orderId", paymentDoneMessage.getOrderId());
        messageContent.put("orderPaymentId", paymentDoneMessage.getPaymentOrderId());
        messageContent.put("status", paymentDoneMessage.getStatus());

        messageMap.put("message", messageContent);
        return messageMap;
    }
}
