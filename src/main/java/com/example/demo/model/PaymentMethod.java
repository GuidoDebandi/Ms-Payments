package com.example.demo.model;

import lombok.Data;

import java.util.UUID;
@Data
public class PaymentMethod {

    private String currency;
    private Boolean enabled;
    private UUID paymentMethodId;
}
