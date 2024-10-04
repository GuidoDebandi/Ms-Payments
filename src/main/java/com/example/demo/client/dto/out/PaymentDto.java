package com.example.demo.client.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class PaymentDto {
    private String cartId;
    private String orderId;
    private String paymentOrderId;
    private Double amount;
}
