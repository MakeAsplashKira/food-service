package com.example.foodservice.OrderService;

import com.example.foodservice.OrderService.dto.CreateOrderCommand;
import com.example.foodservice.OrderService.dto.CreateOrderInfo;
import com.example.foodservice.OrderService.dto.OrderLine;
import com.example.foodservice.OrderService.exception.DuplicateMenuItemException;
import com.example.foodservice.RestaurantService.RestaurantService;
import com.example.foodservice.OrderService.exception.DifferentRestaurantException;
import com.example.foodservice.RestaurantService.dto.MenuItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;

    private static final int MAX_RESTAURANTS_AVAILABLE_FOR_ORDER = 1;


    @Transactional
    public CreateOrderInfo createOrder(CreateOrderCommand command) {
        List<OrderLine> lines = command.lines();

        //1. Проверяем дубликаты
        Map<Long, Integer> linesMap = quantitiesByMenuItemId(lines);

        //2. Передаем во внешний сервис для получения MenuItem
        List<MenuItemInfo> menuItems = restaurantService
                .getMenuItemsByIds(extractIdsFromOrderItemsToList(lines));

        //3. Проверяем, все ли menuItems из одного ресторана
        ensureAllItemsFromSameRestaurant(menuItems);

        //4. теперь через сервис уменьшаем quantity
        restaurantService.decreaseMenuItemQuantity(lines);


        List<OrderItem> orderItems = menuItems
                .stream()
                .map(menuItem -> OrderItem.from(menuItem, linesMap))
                .collect(Collectors.toCollection(ArrayList::new));


        Long restaurantId = menuItems.getFirst().restaurantId();
        Order order = Order.from(orderItems, restaurantId, command.userId(), OrderStatus.PENDING);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepository.save(order);

        return CreateOrderInfo.from(order);
    }

    private List<Long> extractIdsFromOrderItemsToList(List<OrderLine> lines) {
        return lines.stream().map(OrderLine::menuItemId).toList();
    }

    private void ensureAllItemsFromSameRestaurant(List<MenuItemInfo> menuItems) {
        Set<Long> restaurantIds = new HashSet<>();
        menuItems.forEach(menuItem -> restaurantIds.add(menuItem.restaurantId()));

        if(restaurantIds.size() > MAX_RESTAURANTS_AVAILABLE_FOR_ORDER) {
            throw new DifferentRestaurantException();
        }
    }

    private Map<Long, Integer> quantitiesByMenuItemId(List<OrderLine> lines) {
        Map<Long, Integer> quantities = new HashMap<>();
        for(OrderLine line : lines) {
            if(quantities.containsKey(line.menuItemId())) {
                throw new DuplicateMenuItemException(line.menuItemId());
            }
            quantities.put(line.menuItemId(), line.quantity());

        }
        return quantities;
    }

}
