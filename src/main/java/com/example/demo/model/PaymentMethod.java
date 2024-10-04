package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document
public class PaymentMethod {

    @Id
    private String paymentMethodId;
    private String methodName;
    private String url;
    private String resource;
    private Boolean enabled;

}
