package com.example.demo.service;

import com.example.demo.model.PaymentOrder;

public interface PaymentOrderService {
    PaymentOrder createNewOrder(PaymentOrder order);

    PaymentOrder processOrder(String orderId);
}
