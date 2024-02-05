package com.example.demo.service;

import com.example.demo.model.PaymentMethod;

import java.util.Collection;

public interface PaymentMethodService {

    Iterable<PaymentMethod> getMethods();
    PaymentMethod postMethod(PaymentMethod newMethod);
    PaymentMethod deactivatePaymentMethod(String idMethod);
}
