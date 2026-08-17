package com.example.foodservice.RestaurantService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_item")
@Setter
@Getter
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "provider_menu_item_id", nullable = false, unique = true)
    private  Long providerMenuItemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    private String category;

    @Column(nullable = false)
    private Integer availableQuantity;

    public MenuItem(Long providerMenuItemId, String name, BigDecimal unitPrice, String category, Restaurant restaurant, Integer availableQuantity) {
        this.providerMenuItemId = providerMenuItemId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
        this.restaurant = restaurant;
        this.availableQuantity = availableQuantity;
    }
}
