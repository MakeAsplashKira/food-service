package com.example.foodservice.orderservice;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findByUserIdAndBrandIdAndStatus(Long userId, Long brandId, OrderStatus status);
    Optional<Order> findByPaymentId(String paymentId);
    Optional<Order> findByIdAndStoreId(Long orderId, Long storeId);
}
