package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document
public class PaymentOrder {
    @Id
    private String paymentOrderId;
    private String orderId;
    private Float totalAmount;
    private PaymentMethod paymentMethod;
    private String status;
}
