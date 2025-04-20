package com.micro.demo_payment.controller;


import com.micro.demo_payment.dto.PaymentRequest;
import com.micro.demo_payment.model.Payment;
import com.micro.demo_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/details")
    public String getPaymentDetails() {
        return "Payment Details from routing";
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = paymentService.findAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);

    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest request) {
        // Logic to process the payment
        System.out.println("Processing payment for Order ID: " + request.getOrderId() +
                ", Amount: " + request.getAmount());

        return ResponseEntity.ok("Payment processed successfully for Order ID: " + request.getOrderId());
    }

}