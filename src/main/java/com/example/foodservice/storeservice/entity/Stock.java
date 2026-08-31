package com.example.foodservice.storeservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_stock",
                columnNames = {"store_id", "product_id"}
        )
})
@Setter
@Getter
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    public Stock(Store store, Long productId, Integer availableQuantity, BigDecimal unitPrice) {
        this.setStore(store);
        this.productId = productId;
        this.availableQuantity = availableQuantity;
        this.unitPrice = unitPrice;
    }
}
