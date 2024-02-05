package com.example.demo.service.impl;

import com.example.demo.model.PaymentMethod;
import com.example.demo.repository.PaymentMethodRepository;
import com.example.demo.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired PaymentMethodRepository repository;

    @Override
    public Iterable<PaymentMethod> getMethods() {
        return repository.findAll();
    }

    @Override
    public PaymentMethod postMethod(PaymentMethod newMethod) {
        return repository.save(newMethod);
    }

    @Override
    public PaymentMethod deactivatePaymentMethod(String idMethod) {
        PaymentMethod method= repository.findById(idMethod).orElseThrow(); //TODO armar exception y manejo de error para cuando no se encuentra el metodo de pago
        method.setEnabled(false);
        repository.save(method);
        return method;
    }
}
