package com.example.demo.config.rabbit;

import com.example.demo.service.PaymentOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PaymentConsumer {
    @Autowired
    PaymentOrderService paymentOrderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

    @RabbitListener(queues = "${rabbitmq.payment.queue.name}")
    public void receiveMessage(String message) throws JsonProcessingException, SQLException {
        LOGGER.info("Mensaje recibido: {}", message);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode messageJson = objectMapper.readTree(message);
            String methodId = messageJson.get("message").get("methodId").asText();
            String orderId = messageJson.get("message").get("orderId").asText();

            paymentOrderService.createNewOrder(orderId,methodId);

        } catch (Exception e) {
            LOGGER.error("Ha ocurrido un error en el consumo del mensaje.");
            return;
        }
    }
}
