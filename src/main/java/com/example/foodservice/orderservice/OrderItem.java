package com.example.foodservice.orderservice;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {
    private static final int MAX_QUANTITY = 10;
    private static final int INITIAL_QUANTITY = 1;
    private static final int MIN_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    @Column(nullable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String name;
    private BigDecimal unitPrice;
    private String externalProductId;

    @Column(nullable = false)
    private Integer requestedQuantity;

    public void incrementQuantity() {
        this.requestedQuantity++;
    }

    public void decrementQuantity() {
        this.requestedQuantity--;
    }

    public int getMinimumQuantity() {
        return MIN_QUANTITY;
    }

    public int getMaximumQuantity() {
        return MAX_QUANTITY; //TODO: рассчитывать индивидуально для товара по его характеристикам
    }


    public OrderItem(Order order, Long productId) {
        this.setOrder(order);
        this.productId = productId;
        this.requestedQuantity = INITIAL_QUANTITY;
    }
}
