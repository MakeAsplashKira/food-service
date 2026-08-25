package com.example.foodservice.StoreService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Store store;

    @Column(name = "provider_menu_item_id", nullable = false)
    private  Long storeProductId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    private String category;

    @Column(nullable = false)
    private Integer availableQuantity;

    public Product(Long storeProductId, String name, BigDecimal unitPrice, String category, Store store, Integer availableQuantity) {
        this.storeProductId = storeProductId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
        this.store = store;
        this.availableQuantity = availableQuantity;
    }
}
