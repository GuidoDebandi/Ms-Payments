package com.example.demo.service;

import com.example.demo.model.PaymentOrder;

import java.sql.SQLException;

public interface PaymentOrderService {
    PaymentOrder createNewOrder(String orderId,String methodId) throws SQLException;

    void processOrders();
}
