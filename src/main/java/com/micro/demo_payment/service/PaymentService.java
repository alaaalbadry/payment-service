package com.micro.demo_payment.service;


import com.micro.demo_payment.model.Payment;
import com.micro.demo_payment.model.PaymentRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Retryable(value = StaleObjectStateException.class, maxAttempts = 3)
    @Transactional
    public void updatePayment(Long PaymentId, BigDecimal newAmount) {
        Payment Payment = paymentRepository.findById(PaymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Payment.setAmount(newAmount);
        paymentRepository.save(Payment);
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        if (payment.getId() != null) {
            throw new IllegalArgumentException("New Payment cannot have an ID");
        }
        return paymentRepository.save(payment);
    }
}