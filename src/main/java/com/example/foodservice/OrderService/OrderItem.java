package com.example.foodservice.OrderService;


import com.example.foodservice.OrderService.exception.IllegalQuantityStateException;
import com.example.foodservice.RestaurantService.dto.MenuItemInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private Long menuItemId;

    @Column(nullable = false)
    private Long providerMenuItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column
    private String category;

    @Column(nullable = false)
    private Integer quantity;

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }

    public int getMinimumQuantity() {
        return MIN_QUANTITY;
    }

    public int getMaximumQuantity() {
        return MAX_QUANTITY; //TODO: рассчитывать индивидуально для товара по его характеристикам
    }

    public static OrderItem from(MenuItemInfo menuItemInfo, Map<Long, Integer> quantityMap) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(menuItemInfo.id());
        orderItem.setProviderMenuItemId(menuItemInfo.providerMenuItemId());
        orderItem.setName(menuItemInfo.name());
        orderItem.setUnitPrice(menuItemInfo.unitPrice());
        orderItem.setCategory(menuItemInfo.category());
        orderItem.setQuantity(quantityMap.get(menuItemInfo.id()));

        return orderItem;
    }
    
    public static OrderItem from(Order order, MenuItemInfo menuItemInfo) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(menuItemInfo.id());
        orderItem.setProviderMenuItemId(menuItemInfo.providerMenuItemId());
        orderItem.setName(menuItemInfo.name());
        orderItem.setUnitPrice(menuItemInfo.unitPrice());
        orderItem.setCategory(menuItemInfo.category());
        orderItem.setQuantity(INITIAL_QUANTITY);

        orderItem.setOrder(order);

        return orderItem;
    }
}
