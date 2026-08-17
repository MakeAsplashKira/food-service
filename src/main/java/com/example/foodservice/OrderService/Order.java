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
}


