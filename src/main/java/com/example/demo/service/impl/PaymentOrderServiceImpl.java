package com.example.demo.service.impl;

import com.example.demo.model.PaymentMethod;
import com.example.demo.model.PaymentOrder;
import com.example.demo.repository.PaymentOrderRepository;
import com.example.demo.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {
    @Autowired PaymentOrderRepository repository;
    @Override
    public PaymentOrder createNewOrder(PaymentOrder order) {
        return repository.save(order);
    }

    @Override
    public PaymentOrder processOrder(String orderId) {
        PaymentOrder order= repository.findById(orderId).orElseThrow();//TODO armar exception y manejo de error para cuando no se encuentra el metodo de pago
        if(order.getPaymentMethod().getEnabled()){
            //TODO dejar mensaje en cola con los datos de la orden a pagar para que sea consumida por el metodo de pago
        }else{
            throw new RuntimeException(); //TODO dejar mensaje en cola con los datos de la orden a pagar
        }


        return null;
    }
}
