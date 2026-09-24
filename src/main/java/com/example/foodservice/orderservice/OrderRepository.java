package com.example.foodservice.orderservice;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findByUserIdAndBrandIdAndStatus(Long userId, Long brandId, OrderStatus status);
    Optional<Order> findByPaymentId(String paymentId);
    Optional<Order> findByIdAndStoreId(Long orderId, Long storeId);

    @Query("SELECT ord FROM Order ord JOIN FETCH ord.orderItems WHERE userId = :userId and status <> 'CANCELED' and status <> 'DELIVERED' and status <> 'DRAFT'")
    List<Order> findActiveByUserId(Long userId);

    @Query("SELECT ord FROM Order ord JOIN FETCH ord.orderItems WHERE userId = :userId and status in ('CANCELED','DELIVERED')")
    List<Order> findInactiveByUserId(Long userId);
}
