package com.example.foodservice.orderservice.dto;

import com.example.foodservice.orderservice.Order;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutInfo;

public record PricedOrder(
        CheckoutInfo checkoutInfo,
        Order order
) {
    public static PricedOrder from(CheckoutInfo checkoutInfo, Order order) {
        return new PricedOrder(
                checkoutInfo,
                order
        );
    }
}
