package com.example.foodservice.orderservice;

import com.example.foodservice.orderservice.dto.*;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutCommand;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutInfo;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutItemInfo;
import com.example.foodservice.orderservice.dto.CheckoutDTO.GetCheckoutCommand;
import com.example.foodservice.orderservice.dto.OrderDTO.AddOrderItemCommand;
import com.example.foodservice.orderservice.dto.OrderDTO.GetOrderCommand;
import com.example.foodservice.orderservice.dto.OrderItemQuantityDTO.SetItemQuantityCommand;
import com.example.foodservice.orderservice.dto.OrderItemQuantityDTO.UpdateItemQuantityCommand;
import com.example.foodservice.orderservice.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductCatalogByOrder productCatalog;
    private final UserCatalog userCatalog;
    private final StoreCatalog storeCatalog;

    private static final int MAX_RESTAURANTS_AVAILABLE_FOR_ORDER = 1;


    @Transactional
    public OrderInfo addOrderItem(AddOrderItemCommand command) {
        if (!productCatalog.existsByBrandIdAndProductId(command.brandId(), command.productId())) {
            throw new BrandOrProductNotFoundException(command.brandId(), command.productId());
        }

        Order order = orderRepository.findByUserIdAndBrandIdAndStatus(command.userId(), command.brandId(), OrderStatus.DRAFT)
                .orElseGet(command::toOrder);

        order.addOrderItem(command.productId());

        orderRepository.save(order);

        return OrderInfo.from(order);
    }

    @Transactional
    public void incrementItemQuantity(UpdateItemQuantityCommand command) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderUserId(command.orderItemId(), command.userId())
                .orElseThrow(() -> new OrderItemNotFoundException(command.orderItemId()));

        Order order = orderItem.getOrder();

        if (!order.canBeModified()) {
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

        if (!order.canBeModified()) {
            throw new OrderUnmodifiableException(order.getStatus());
        }

        if (order.decrementOrderItemQuantity(orderItem)) {
            orderRepository.save(order);
        } else {
            orderRepository.delete(order);
        }

    }

    @Transactional
    public void setItemQuantity(SetItemQuantityCommand command) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderUserId(command.orderItemId(), command.userId())
                .orElseThrow(() -> new OrderItemNotFoundException(command.orderItemId()));

        Order order = orderItem.getOrder();

        if (!order.canBeModified()) {
            throw new OrderUnmodifiableException(order.getStatus());
        }

        order.setOrderItemQuantity(orderItem, command.requestedQuantity());
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public OrderInfo getDraftOrder(GetOrderCommand command) {
        Order order = orderRepository.findByUserIdAndBrandIdAndStatus(command.userId(), command.brandId(), OrderStatus.DRAFT)
                .orElseThrow(() -> new OrderNotFoundException(command.userId(), command.brandId()));

        return OrderInfo.from(order);
    }



    private PricedOrder buildPricedOrder(Long userId, Long brandId) {
        Order order = orderRepository.findByUserIdAndBrandIdAndStatus(userId, brandId, OrderStatus.DRAFT)
                .orElseThrow(() -> new OrderNotFoundException(userId, brandId));

        UserInfo userInfo = userCatalog.getUserInfo(userId);

        List<Long> productIds = order.extractProductIdsFromItems();

        //TODO: опять же дальше будем передавать longitude, latitude, пока что просто адрес как заглушку
        Long storeId = storeCatalog.getStoreIdByBrandIdAndUserAdress(brandId, userInfo.address());
        List<StockInfo> stockInfo = storeCatalog.findStockByStoreIdAndProductIds(storeId, productIds);

        List<ProductInfo> productInfo = productCatalog.getProductsByIdsForOrder(productIds);

        //Если не хватает предмета, будет просто CheckoutItemInfo с null полями, кроме productId и с available=false
        List<CheckoutItemInfo> checkoutItemInfo = CheckoutItemInfo.from(productInfo, stockInfo, order.getOrderItems());
        CheckoutInfo checkoutInfo = CheckoutInfo.from(storeId, order, checkoutItemInfo, userInfo);


        return PricedOrder.from(checkoutInfo, order);
    }

    @Transactional(readOnly = true)
    public CheckoutInfo getCheckoutDetails(GetCheckoutCommand command) {
        return buildPricedOrder(command.userId(), command.brandId()).checkoutInfo();
    }

    @Transactional
    public void checkoutOrder(CheckoutCommand command) {
        PricedOrder pricedOrder = buildPricedOrder(command.userId(), command.brandId());

        Order order = pricedOrder.order();


        CheckoutInfo checkoutInfo = pricedOrder.checkoutInfo();

        Map<Long, CheckoutItemInfo> checkoutItemMap = checkoutInfo.checkoutItems().stream()
                .collect(Collectors.toMap(CheckoutItemInfo::orderItemId, Function.identity()));

        for(OrderItem orderItem: order.getOrderItems()) {
            CheckoutItemInfo checkoutItem = checkoutItemMap.get(orderItem.getId());

            if(checkoutItem.available()){
                order.snapshotOrderItem(orderItem, checkoutItem);
            } else throw new OrderItemUnavailableException(orderItem.getId());
        }

        order.prepareForPayment(checkoutInfo,
                command.commentToStore(),
                command.commentToCourier(),
                command.paymentMethod());

    }
}
