package com.micro.demo_payment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
    // Getters and setters
}