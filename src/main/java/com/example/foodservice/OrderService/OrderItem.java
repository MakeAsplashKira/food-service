package com.example.foodservice.OrderService;


import com.example.foodservice.common.dto.MenuItemResponse;
import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long providerItemMenuId;

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

    public static OrderItem from(MenuItemResponse menuItemResponses) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProviderItemMenuId(menuItemResponses.providerMenuItemId());
        orderItem.setName(menuItemResponses.name());
        orderItem.setUnitPrice(menuItemResponses.unitPrice());
        orderItem.setCategory(menuItemResponses.category());
        orderItem.setQuantity(menuItemResponses.quantity());

        return orderItem;
    }
}
