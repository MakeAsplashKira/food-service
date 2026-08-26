package com.example.foodservice.OrderService;

import com.example.foodservice.OrderService.exception.IllegalQuantityStateException;
import com.example.foodservice.StoreService.dto.ProductInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "pending_at")
    private Instant pendingAt;

    @Column(name = "delivered_at")
    private Instant deliveredAt;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public Order(List<OrderItem> orderItems, Long restaurantId, Long userId) {
        this.orderItems = orderItems;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.status = OrderStatus.DRAFT;
    }
    public Order(Long userId, Long restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.status = OrderStatus.DRAFT;

    }

    public void incrementOrderItemQuantity(OrderItem orderItem) {
        int MAX_QUANTITY = orderItem.getMaximumQuantity();

        if(orderItem.getQuantity() >= MAX_QUANTITY) {
            throw new IllegalQuantityStateException(orderItem.getMenuItemId(), MAX_QUANTITY);
        }
        orderItem.incrementQuantity();
    }
    public void decrementOrderItemQuantity(OrderItem orderItem) {
        if(orderItem.getQuantity() <= orderItem.getMinimumQuantity()) {
            this.removeOrderItem(orderItem);
            return;
        }
        orderItem.decrementQuantity();
    }

    public void addOrderItem(ProductInfo productInfo) {
        Optional<OrderItem> orderItem = this.orderItems.stream()
                .filter((item) -> item.getMenuItemId().equals(productInfo.id()))
                .findFirst();

        if(orderItem.isPresent()) {
             orderItem.get().incrementQuantity();
        } else {
            OrderItem newOrderItem = OrderItem.from(this, productInfo);
            this.orderItems.add(newOrderItem);
        }
    }

    public void removeOrderItem(OrderItem orderItem) {
        if(this.orderItems.remove(orderItem)) {
            orderItem.setOrder(null);
        }
    }

    public static Order from(List<OrderItem> orderItems, Long restaurantId, Long userId) {
        return new Order(
                orderItems,
                restaurantId,
                userId
        );

    }

    public static Order fromAddItemCommand(Long userId, Long restaurantId) {
        return new Order(
                userId,
                restaurantId
        );
    }
}


