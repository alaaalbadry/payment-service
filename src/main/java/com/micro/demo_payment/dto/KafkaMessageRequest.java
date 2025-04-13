package com.micro.demo_payment.dto;

public class KafkaMessageRequest {
    private String topic;
    private String message;

    // Getters & Setters
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
