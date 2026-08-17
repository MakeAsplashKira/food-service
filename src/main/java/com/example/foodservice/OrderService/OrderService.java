package com.example.foodservice.OrderService;

import com.example.foodservice.OrderService.dto.CreateOrderResponse;
import com.example.foodservice.RestaurantService.RestaurantService;
import com.example.foodservice.common.dto.MenuItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;

    public CreateOrderResponse createOrder(Map<String, Integer> menuItemsIds) {
        // Пока не уверен как с такой реализацией аутентифицировать пользователя, кроме как копирования методов из других сервисов, пока оставлю без проверки

        List<MenuItemResponse> menuItems = restaurantService.getMenuItemsByIds(menuItemsIds); // здесь проверили что такие позиции существуют, у каждой  позиции один и тот же ресторан
        // по поводу того что я использовал сервис, правильно ли я понимаю что в будущем будет типо restaurantClient и
        // через него я буду получать данные, он будет обрабатывать ошибки и ловить сами http запросы и отдает дто просто уже готовую, либо кидает ошибку и я через @RestControllerADvice ее обрабатываю?

        List<OrderItem> orderItems = menuItems
                .stream()
                .map(OrderItem::from)
                .toList();

        Long restaurantId = menuItems.getFirst().restaurantId(); // не знаю насколько правильно решение так делать, ну ошибок вызвать оно не должно, я про в целом, отдавать id в каждом MenuItemResponse только для этого...

        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setRestaurantId(restaurantId);
        order.setUserId(1L);
        order.setStatus(OrderStatus.PENDING);

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepository.save(order);

        return CreateOrderResponse.from(order);
    }
}
