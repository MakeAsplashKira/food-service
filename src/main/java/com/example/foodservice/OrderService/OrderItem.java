package com.example.foodservice.OrderService;


import com.example.foodservice.OrderService.exception.IllegalQuantityStateException;
import com.example.foodservice.RestaurantService.dto.MenuItemInfo;
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
    private static final Integer MAX_QUANTITY = 10;
    private static final Integer INITIAL_QUANTITY = 1;
    private static final Integer MIN_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long menuItemId;

    @Column(nullable = false)
    Long providerMenuItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    BigDecimal unitPrice;

    @Column
    String category;

    @Column(nullable = false)
    Integer quantity;

    public void incrementQuantity() {
        if(this.quantity >= MAX_QUANTITY) {
            throw new IllegalQuantityStateException(this.menuItemId, MAX_QUANTITY);
        }
        this.quantity++;
    }

    public void decrementQuantity() {
        if(this.quantity <= MIN_QUANTITY) {
            return; //TODO: добавить удаление OrderItem
        }
        this.quantity--;
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
