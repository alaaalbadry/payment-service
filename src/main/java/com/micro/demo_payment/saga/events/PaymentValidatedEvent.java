package com.micro.demo_payment.saga.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentValidatedEvent {
    private Long orderId;
    private String status; // "VALIDATED", "FAILED", etc.
    private String message;
}
