package com.example.foodservice.OrderService;

import com.example.foodservice.RestaurantService.dto.MenuItemInfo;
import jakarta.persistence.*;
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
    private static final Integer INITIAL_QUANTITY = 1;

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
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    Long restaurantId;

    @Column(nullable = false)
    Long userId;

    @CreationTimestamp
    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "pending_at")
    Instant pendingAt;

    @Column(name = "delivered_at")
    Instant deliveredAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

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
        orderItem.incrementQuantity();
    }
    public void decrementOrderItemQuantity(OrderItem orderItem) {
        if(orderItem.getQuantity() <= orderItem.getMinimumQuantity()) {
            this.removeOrderItem(orderItem);
            return;
        }
        orderItem.decrementQuantity();
    }

    public void addOrderItem(MenuItemInfo menuItemInfo) {
        Optional<OrderItem> orderItem = this.orderItems.stream()
                .filter((item) -> item.menuItemId.equals(menuItemInfo.id()))
                .findFirst();

        if(orderItem.isPresent()) {
             orderItem.get().incrementQuantity();
        } else {
            OrderItem newOrderItem = OrderItem.from(this, menuItemInfo);
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


