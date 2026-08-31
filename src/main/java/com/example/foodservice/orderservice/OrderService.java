package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.dto.*;
import com.example.foodservice.orderservice.exception.DuplicateMenuItemException;
import com.example.foodservice.orderservice.exception.OrderItemNotFoundException;
import com.example.foodservice.orderservice.exception.OrderUnmodifiableException;
import com.example.foodservice.storeservice.StoreService;
import com.example.foodservice.orderservice.exception.DifferentRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StoreService storeService;

    private static final int MAX_RESTAURANTS_AVAILABLE_FOR_ORDER = 1;


//    @Transactional
//    public CreateOrderInfo createOrder(CreateOrderCommand command) {
//        List<OrderLine> lines = command.lines();
//
//        //1. Проверяем дубликаты
//        Map<Long, Integer> linesMap = quantitiesByMenuItemId(lines);
//
//        //2. Передаем во внешний сервис для получения MenuItem
//        List<ProductInfo> menuItems = storeService
//                .getProductsByIds(extractIdsFromOrderItemsToList(lines));
//
//        //3. Проверяем, все ли menuItems из одного ресторана
//        ensureAllItemsFromSameRestaurant(menuItems);
//
//        //4. теперь через сервис уменьшаем quantity
//        storeService.decreaseProductQuantity(lines);
//
//
//        List<OrderItem> orderItems = menuItems
//                .stream()
//                .map(menuItem -> OrderItem.from(menuItem, linesMap))
//                .collect(Collectors.toCollection(ArrayList::new));
//
//
//        Long restaurantId = menuItems.getFirst().restaurantId();
//        Order order = Order.from(orderItems, restaurantId, command.userId());
//        orderItems.forEach(orderItem -> orderItem.setOrder(order));
//
//        orderRepository.save(order);
//
//        return CreateOrderInfo.from(order);
//    }

//    @Transactional
//    public OrderInfo addItem(AddItemCommand command) {
//        ProductInfo productInfo = storeService.getProductByIdAndStoreId(command.productId(), command.storeId());
//
//        Order order = orderRepository.findByUserIdAndStoreId(command.userId(), command.storeId())
//                .orElseGet(command::toOrder);
//
//        if( ! order.canBeModified()) {
//            throw new OrderUnmodifiableException(order.getStatus());
//        }
//
//        order.addOrderItem(productInfo);
//
//        orderRepository.save(order);
//
//        return OrderInfo.from(order);
//    }

    @Transactional
    public void incrementItemQuantity(UpdateItemQuantityCommand command) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderUserId(command.orderItemId(), command.userId())
                .orElseThrow(() -> new OrderItemNotFoundException(command.orderItemId()));

        Order order = orderItem.getOrder();

        if( ! order.canBeModified()) {
            throw new OrderUnmodifiableException(order.getStatus());
        }

        order.incrementOrderItemQuantity(orderItem);

        orderRepository.save(order);
    }

    @Transactional
    public void decreaseItemQuantity(UpdateItemQuantityCommand command) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderUserId(command.orderItemId(), command.userId())
                .orElseThrow(() -> new OrderItemNotFoundException(command.orderItemId()));

        Order order = orderItem.getOrder();

        if( ! order.canBeModified()) {
            throw new OrderUnmodifiableException(order.getStatus());
        }

        order.decrementOrderItemQuantity(orderItem);

        orderRepository.save(order);
    }

    private List<Long> extractIdsFromOrderItemsToList(List<OrderLine> lines) {
        return lines.stream().map(OrderLine::menuItemId).toList();
    }

//    private void ensureAllItemsFromSameRestaurant(List<ProductInfo> menuItems) {
//        Set<Long> restaurantIds = new HashSet<>();
//        menuItems.forEach(menuItem -> restaurantIds.add(menuItem.restaurantId()));
//
//        if(restaurantIds.size() > MAX_RESTAURANTS_AVAILABLE_FOR_ORDER) {
//            throw new DifferentRestaurantException();
//        }
//    }

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
