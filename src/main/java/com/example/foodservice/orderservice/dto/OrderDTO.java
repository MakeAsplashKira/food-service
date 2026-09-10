package com.example.foodservice.orderservice.dto;


import com.example.foodservice.orderservice.Order;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public final class OrderDTO {
    private OrderDTO() {}

    public record AddOrderItemRequest(
            @NotNull @Positive Long brandId,
            @NotNull @Positive Long productId
    ) {
        public AddOrderItemCommand toCommand(Long userId) {
               return new AddOrderItemCommand(userId, brandId, productId);
        }
    }

    public record AddOrderItemCommand(Long userId, Long brandId, Long productId){
        public Order toOrder(){
            return new Order(
                    this.userId,
                    this.brandId
            );
        }
    }

    public record GetOrderCommand(Long userId, Long brandId) {}
}
