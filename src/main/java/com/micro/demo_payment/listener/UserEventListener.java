package com.micro.demo_payment.listener;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @KafkaListener(topics = "user-events", groupId = "payment-service-group")
    public void listenUserEvents(String message) {
        System.err.println("Received User Event in Order Service: " + message);
        // Add your business logic to process the event
    }
}