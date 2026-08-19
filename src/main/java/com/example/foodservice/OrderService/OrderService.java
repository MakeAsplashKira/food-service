package com.example.foodservice.OrderService;

import com.example.foodservice.OrderService.dto.CreateOrderCommand;
import com.example.foodservice.OrderService.dto.CreateOrderInfo;
import com.example.foodservice.OrderService.dto.OrderItemsRequest;
import com.example.foodservice.OrderService.dto.OrderLine;
import com.example.foodservice.OrderService.exception.DuplicateMenuItemException;
import com.example.foodservice.RestaurantService.RestaurantService;
import com.example.foodservice.OrderService.exception.DifferentRestaurantException;
import com.example.foodservice.common.dto.MenuItemInfo;
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
        //1. Проверяем дубликаты
       ensureItemsNotDuplicated(command.lines());

        //2. Передаем во внешний сервис для получения MenuItem
        List<MenuItemInfo> menuItems = restaurantService
                .getMenuItemsByIds(extractIdsFromOrderItemsToList(command.lines()));

        //3. Проверяем, все ли menuItems из одного ресторана
        ensureAllItemsFromSameRestaurant(menuItems);

        //4. теперь через сервис уменьшаем quantity
        restaurantService.decreaseMenuItemQuantity(command.lines());

        Map<Long, Integer> itemsQuantityMap = command.lines().stream()
                .collect(Collectors.toMap(
                        OrderLine::menuItemId,
                        OrderLine::quantity
                ));

        List<OrderItem> orderItems = menuItems
                .stream()
                .map(menuItem -> OrderItem.from(menuItem, itemsQuantityMap))
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

    private void ensureItemsNotDuplicated(List<OrderLine> lines) {
        HashMap<Long, Integer> uniqueItemsIds = new HashMap<>();
        lines.forEach((line) -> uniqueItemsIds.put(line.menuItemId(), line.quantity()));
        if(lines.size() != uniqueItemsIds.size()) {
            throw new DuplicateMenuItemException();
        }

    }

}
