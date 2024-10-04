package com.example.demo.service;

import com.example.demo.model.PaymentMethod;

import java.util.Collection;
import java.util.Optional;

public interface PaymentMethodService {

    Iterable<PaymentMethod> getMethods();
    Optional<PaymentMethod> getMethodById(String methodId);
    PaymentMethod postMethod(PaymentMethod newMethod);
    PaymentMethod deactivatePaymentMethod(String idMethod);
}
