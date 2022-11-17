package com.example.demo.model;

import lombok.Data;

import java.util.UUID;
@Data
public class PaymentOrder {
    private UUID orderId;
    private Float totalAmount;
    private String userId;
    private PaymentMethod paymentMethod;
}
