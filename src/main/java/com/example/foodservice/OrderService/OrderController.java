package com.example.foodservice.OrderService;


import com.example.foodservice.OrderService.dto.*;
import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                                                        @AuthenticationPrincipal Long userId) {

        CreateOrderInfo orderInfo = orderService.createOrder(request.toCommand(userId));

        return responseBuilder.created(CreateOrderResponse.from(orderInfo));
    }

    @Validated
    @PostMapping(value = "/add-item")
    public ResponseEntity<ApiResponse<OrderInfo>> addItem(
            @Positive @RequestParam Long restaurantId,
            @Positive @RequestParam Long menuItemId,
            @AuthenticationPrincipal Long userId) {

        AddItemCommand command = AddItemCommand.from(userId, restaurantId, menuItemId);

        OrderInfo orderInfo = orderService.addItem(command);

        return responseBuilder.ok(orderInfo);
    }
    @PatchMapping(value = "/items/{orderItemId}/increment")
    public ResponseEntity<ApiResponse<Void>> incrementItemQuantity(
            @PathVariable Long orderItemId,
            @AuthenticationPrincipal Long userId) {

        UpdateItemQuantityCommand command = UpdateItemQuantityCommand.from(userId, orderItemId);

        orderService.incrementItemQuantity(command);

        return responseBuilder.ok(null);
    }

    @PatchMapping(value = "/items/{orderItemId}/decrement")
    public ResponseEntity<ApiResponse<Void>> decrementItemQuantity(
            @PathVariable Long orderItemId,
            @AuthenticationPrincipal Long userId) {
        UpdateItemQuantityCommand command = UpdateItemQuantityCommand.from(userId, orderItemId);

        orderService.decreaseItemQuantity(command);

        return responseBuilder.ok(null);
    }
}
