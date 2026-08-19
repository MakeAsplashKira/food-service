package com.example.foodservice.OrderService;


import com.example.foodservice.common.dto.MenuItemInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long menuItemId;

    @Column(nullable = false)
    Long providerMenuItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order = new Order();

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    BigDecimal unitPrice;

    @Column
    String category;

    @Column(nullable = false)
    Integer quantity;

    public static OrderItem from(MenuItemInfo menuItemResponses, Map<Long, Integer> quantityMap) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(menuItemResponses.id());
        orderItem.setProviderMenuItemId(menuItemResponses.providerMenuItemId());
        orderItem.setName(menuItemResponses.name());
        orderItem.setUnitPrice(menuItemResponses.unitPrice());
        orderItem.setCategory(menuItemResponses.category());
        orderItem.setQuantity(quantityMap.get(menuItemResponses.id()));

        return orderItem;
    }
}
