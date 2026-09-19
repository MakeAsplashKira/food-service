package com.example.foodservice.orderservice.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class FakePaymentGateway implements PaymentGateway {
    private static final String LINK = "http://localhost:8080/fake-pay/%s";

    @Override
    public PaymentInfo createPayment(Long orderId, BigDecimal totalPrice) {
        String id = UUID.randomUUID().toString();
        return PaymentInfo.from(id, LINK.formatted(id));
    }
}
