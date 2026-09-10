package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutInfo;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutItemInfo;
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

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "pending_at")
    private Instant pendingAt;

    @Column(name = "delivered_at")
    private Instant deliveredAt;

    @Column(nullable = false)
    private Long brandId;

    @Column(nullable = false)
    private Long userId;

    private Long storeId;
    private String address;
    private String commentToStore;
    private String commentToCourier;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Order(Long userId, Long brandId) {
        this.userId = userId;
        this.brandId = brandId;
        this.status = OrderStatus.DRAFT;
    }

    public void incrementOrderItemQuantity(OrderItem orderItem) {
        int MAX_QUANTITY = orderItem.getMaximumQuantity();

        if (orderItem.getRequestedQuantity() >= MAX_QUANTITY) {
            throw new IllegalQuantityStateException(orderItem.getProductId(), MAX_QUANTITY);
        }
        orderItem.incrementQuantity();
    }

    public boolean decrementOrderItemQuantity(OrderItem orderItem) {
        if (orderItem.getRequestedQuantity() <= orderItem.getMinimumQuantity()) {
            this.removeOrderItem(orderItem);

            return !this.orderItems.isEmpty();
        }
        orderItem.decrementQuantity();
        return true;
    }

    public void setOrderItemQuantity(OrderItem orderItem, Integer requestedQuantity) {
        int maxQuantity = orderItem.getMaximumQuantity();
        if(requestedQuantity > maxQuantity) {
            throw new IllegalQuantityStateException(orderItem.getProductId(), maxQuantity);
        } else {
            orderItem.setRequestedQuantity(requestedQuantity);
        }
    }

    public void addOrderItem(Long productId) {
        this.orderItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        this::incrementOrderItemQuantity,
                        () -> this.orderItems.add(new OrderItem(this, productId))
                );

    }
    public void snapshotOrderItem(OrderItem orderItem, CheckoutItemInfo checkoutItem){
        orderItem.setName(checkoutItem.name());
        orderItem.setUnitPrice(checkoutItem.unitPrice());
        orderItem.setExternalProductId(checkoutItem.externalProductId());
    }

    public void snapshotOrder(CheckoutInfo checkoutInfo, String commentToStore, String commentToCourier, PaymentMethod paymentMethod) {
        this.setAddress(checkoutInfo.userInfo().address());
        this.setStoreId(checkoutInfo.storeId());
        this.setCommentToStore(commentToStore);
        this.setCommentToCourier(commentToCourier);
        this.setPaymentMethod(paymentMethod);
        this.setStatus(OrderStatus.AWAITING_PAYMENT);
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (this.orderItems.remove(orderItem)) {
            orderItem.setOrder(null);
        }
    }

    public boolean canBeModified() {
        return this.status == OrderStatus.DRAFT;
    }

    public List<Long> extractProductIdsFromItems(){
        return this.orderItems.stream().map(OrderItem::getProductId).toList();
    }
}


