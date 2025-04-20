package com.micro.demo_payment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PaymentRequest {
    // Getters and setters
    private String orderId;
    private double amount;

}

