package com.example.foodservice.orderservice.payment;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentInfo createPayment(Long orderId, BigDecimal totalPrice);
}
