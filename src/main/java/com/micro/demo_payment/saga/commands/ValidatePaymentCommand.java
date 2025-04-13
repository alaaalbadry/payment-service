package com.micro.demo_payment.saga.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatePaymentCommand {
    private Long orderId;
    private Long userId;
    private String paymentMethod;
    private Double amount;
}
