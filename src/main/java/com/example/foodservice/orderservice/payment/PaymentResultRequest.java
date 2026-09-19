package com.example.foodservice.orderservice.payment;

import jakarta.validation.constraints.NotBlank;

public record PaymentResultRequest(
        @NotBlank String paymentId,
        @NotBlank String status
) {
    public PaymentResultCommand toCommand() {
        return new PaymentResultCommand(
                this.paymentId,
                this.status
        );
    }
}
