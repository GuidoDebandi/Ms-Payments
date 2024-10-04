package com.example.demo.service.impl;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.PaymentMethod;
import com.example.demo.repository.PaymentMethodRepository;
import com.example.demo.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired PaymentMethodRepository repository;

    @Override
    public Iterable<PaymentMethod> getMethods() {
        return repository.findByEnabledTrue();
    }

    @Override
    public Optional<PaymentMethod> getMethodById(String methodId) {
        return repository.findById(methodId);
    }

    @Override
    public PaymentMethod postMethod(PaymentMethod newMethod) {
        return repository.save(newMethod);
    }

    @Override
    public PaymentMethod deactivatePaymentMethod(String idMethod) {
        PaymentMethod method= repository.findById(idMethod)
                .orElseThrow(()->new NotFoundException("No ha sido posible recuperar el metodo de pago a deshabilitar"));
        method.setEnabled(false);
        repository.save(method);
        return method;
    }
}
