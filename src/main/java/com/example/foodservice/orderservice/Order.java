package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.exception.IllegalQuantityStateException;
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
    private Long storeId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public Order(List<OrderItem> orderItems, Long storeId, Long userId) {
        this.orderItems = orderItems;
        this.storeId = storeId;
        this.userId = userId;
        this.status = OrderStatus.DRAFT;
    }
    public Order(Long userId, Long storeId) {
        this.userId = userId;
        this.storeId = storeId;
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

//    public void addOrderItem(ProductInfo productInfo) {
//        Optional<OrderItem> orderItem = this.orderItems.stream()
//                .filter((item) -> item.getMenuItemId().equals(productInfo.id()))
//                .findFirst();
//
//        if(orderItem.isPresent()) {
//             orderItem.get().incrementQuantity();
//        } else {
//            OrderItem newOrderItem = OrderItem.from(this, productInfo);
//            this.orderItems.add(newOrderItem);
//        }
//    }

    public void removeOrderItem(OrderItem orderItem) {
        if(this.orderItems.remove(orderItem)) {
            orderItem.setOrder(null);
        }
    }

    public boolean canBeModified() {
        return this.status == OrderStatus.DRAFT;
    }

}


