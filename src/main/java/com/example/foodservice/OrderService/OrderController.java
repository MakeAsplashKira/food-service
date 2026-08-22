package com.example.foodservice.OrderService;


import com.example.foodservice.OrderService.dto.CreateOrderInfo;
import com.example.foodservice.OrderService.dto.CreateOrderRequest;
import com.example.foodservice.OrderService.dto.CreateOrderResponse;
import com.example.foodservice.OrderService.dto.OrderItemsRequest;
import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "order")
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
}
