package com.micro.demo_payment.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.demo_payment.saga.OrderCreatedEvent;
import com.micro.demo_payment.saga.commands.ValidatePaymentCommand;
import com.micro.demo_payment.saga.events.PaymentValidatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class OrderEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public OrderEventListener(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //payment listen to order orchestrator
//    @KafkaListener(topics = "validate-payment", groupId = "payment", containerFactory = "validatePaymentKafkaListenerContainerFactory")
//    public void handleValidatePayment(ValidatePaymentCommand command) {
//        System.out.println("Received Validate Payment command in Order Service: " + command);
//        PaymentValidatedEvent event = new PaymentValidatedEvent(
//                command.getOrderId(),
//                "VALIDATED",
//                "Payment validated successfully"
//        );
//        String payload = null;
//        try {
//            payload = objectMapper.writeValueAsString(event);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        kafkaTemplate.send("payment-validated", payload);
//    }

}