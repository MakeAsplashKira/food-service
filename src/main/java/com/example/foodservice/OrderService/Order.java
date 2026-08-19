package com.example.foodservice.OrderService;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    Long restaurantId;

    @Column(nullable = false)
    Long userId;

    @CreationTimestamp
    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "delivered_at")
    Instant deliveredAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    public Order(List<OrderItem> orderItems, Long restaurantId, Long userId, OrderStatus status) {
        this.orderItems = orderItems;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.status = status;
    }

    public static Order from(List<OrderItem> orderItems, Long restaurantId, Long userId, OrderStatus status) {
        return new Order(
                orderItems,
                restaurantId,
                userId,
                status
        );

    }
}


