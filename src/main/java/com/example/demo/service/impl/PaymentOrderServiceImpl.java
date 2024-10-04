package com.example.demo.service.impl;


import com.example.demo.client.dto.in.OrderDto;
import com.example.demo.client.dto.out.PaymentDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.PaymentMethod;
import com.example.demo.config.rabbit.PaymentTopicProducer;
import com.example.demo.model.PaymentOrder;
import com.example.demo.repository.PaymentOrderRepository;
import com.example.demo.service.PaymentMethodService;
import com.example.demo.service.PaymentOrderService;
import com.example.demo.utils.StatusPaymentOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Date;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentOrderServiceImpl.class);

    @Value("${getOrder.url}")
    String ORDERS_URL;

    @Value("${token.bearer}")
    String TOKEN;
    @Autowired PaymentOrderRepository repository;
    @Autowired PaymentMethodService methodService;

    @Autowired PaymentTopicProducer producer;

    @Autowired private final RestTemplate restTemplate;

    public PaymentOrderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PaymentOrder createNewOrder(String orderId, String methodId) {
        PaymentMethod method = methodService.getMethodById(methodId)
                .orElseThrow(()->new NotFoundException("No ha sido posible recuperar el metodo solicitado"));
        PaymentOrder order = PaymentOrder.builder()
                .status(StatusPaymentOrder.PLACED.name())
                .orderId(orderId)
                .paymentMethod(method)
                .build();
        return repository.save(order);
    }

    @Override
    @Scheduled(cron = "${process.orders.cron}")
    public void processOrders() {
        Iterable<PaymentOrder> placedOrders = repository.findByStatusContainingIgnoreCase(StatusPaymentOrder.PLACED.name());


        if (((Collection<?>) placedOrders).isEmpty()) {
            LOGGER.warn("Ejecucion {}, no se ha obtenido ninguna orden nueva",new Date());
        }
        for (PaymentOrder paymentOrder : placedOrders) {
                validateAndProcessOrder(paymentOrder);
        }
    }

    private void validateAndProcessOrder(PaymentOrder order) {
        OrderDto associatedOrder;
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", TOKEN);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String resource = ORDERS_URL + order.getOrderId();

            ResponseEntity<OrderDto> response = restTemplate.exchange(resource, HttpMethod.GET, entity, OrderDto.class);
            associatedOrder = response.getBody();
        } catch (Exception e) {
            LOGGER.error("No fue posible conseguir la orden asociada al pago {}", order.getPaymentOrderId());
            return;
        }
        if (associatedOrder != null && associatedOrder.getStatus().equals("VALIDATED")) {
            PaymentMethod paymentMethod = order.getPaymentMethod();
            if (paymentMethod != null) {

                String fullUrl = paymentMethod.getUrl() + paymentMethod.getResource();
                PaymentDto body = new PaymentDto(associatedOrder.getCartId(), associatedOrder.getId(), order.getPaymentOrderId(), associatedOrder.getTotalPrice());

                RequestEntity<PaymentDto> requestEntity = RequestEntity
                        .post(fullUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(body);
                try {
                    restTemplate.exchange(requestEntity, String.class);
                    LOGGER.info("Cobro de la orden {} realizado con exito", order.getOrderId());
                    order.setStatus(StatusPaymentOrder.PAID.name());
                }catch (HttpClientErrorException ex){
                    LOGGER.warn("No ha sido posible realizar el cobro de la orden {}, el pago se toma como fallido", order.getOrderId());
                    order.setStatus(StatusPaymentOrder.FAILED.name());
                } catch (Exception e) {
                    LOGGER.error("Ha ocurrido un error inesperado durante el procesamiento de la orden {}, el pago se toma como ambiguo", order.getOrderId());
                    LOGGER.error("Exception message: {}",e.getMessage());
                    order.setStatus(StatusPaymentOrder.AMBIGUOUS.name());
                }

                LOGGER.info("Se notifica al servicio de Orders del resultado del pago...");
                producer.sendMessage(order);
            } else {
                LOGGER.warn("El método de pago asociado es nulo para la orden con ID: {}", order.getOrderId());
                order.setStatus(StatusPaymentOrder.FAILED.name());
            }
            repository.save(order);

        }
    }
}
