package com.example.foodservice.orderservice;


import com.example.foodservice.common.security.principal.UserPrincipal;
import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutInfo;
import com.example.foodservice.orderservice.dto.CheckoutDTO.CheckoutRequest;
import com.example.foodservice.orderservice.dto.CheckoutDTO.ViewCheckoutResponse;
import com.example.foodservice.orderservice.dto.CheckoutDTO.GetCheckoutCommand;
import com.example.foodservice.orderservice.dto.OrderDTO;
import com.example.foodservice.orderservice.dto.OrderDTO.GetOrderCommand;
import com.example.foodservice.orderservice.dto.OrderInfo;
import com.example.foodservice.orderservice.dto.OrderItemQuantityDTO.UpdateItemQuantityCommand;
import com.example.foodservice.orderservice.dto.OrderItemQuantityDTO.SetItemQuantityRequest;
import com.example.foodservice.orderservice.payment.PaymentInfo;
import com.example.foodservice.orderservice.payment.PaymentResponse;
import com.example.foodservice.orderservice.payment.PaymentResultRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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


    @PostMapping(value = "/item")
    public ResponseEntity<ApiResponse<OrderInfo>> addItem(
            @Valid @RequestBody OrderDTO.AddOrderItemRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {


        OrderInfo orderInfo = orderService.addOrderItem(request.toCommand(userPrincipal.userId()));

        return responseBuilder.ok(orderInfo);
    }
    @PatchMapping(value = "/item/{orderItemId}/increment")
    public ResponseEntity<ApiResponse<Void>> incrementItemQuantity(
            @PathVariable Long orderItemId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        UpdateItemQuantityCommand command = UpdateItemQuantityCommand.from(userPrincipal.userId(), orderItemId);

        orderService.incrementItemQuantity(command);

        return responseBuilder.ok(null);
    }

    @PatchMapping(value = "/item/{orderItemId}/decrement")
    public ResponseEntity<ApiResponse<Void>> decrementItemQuantity(
            @PathVariable Long orderItemId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UpdateItemQuantityCommand command = UpdateItemQuantityCommand.from(userPrincipal.userId(), orderItemId);

        orderService.decreaseItemQuantity(command);

        return responseBuilder.ok(null);
    }

    @PatchMapping(value = "/item/{orderItemId}")
    public ResponseEntity<ApiResponse<Void>> setItemQuantity(
            @Valid @RequestBody SetItemQuantityRequest request,
            @PathVariable Long orderItemId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        orderService.setItemQuantity(request.toCommand(userPrincipal.userId(), orderItemId));

        return responseBuilder.ok(null);
    }


    @GetMapping
    @Validated
    public ResponseEntity<ApiResponse<OrderInfo>> getOrder(
            @RequestParam @NotNull @Min(1) Long brandId,
            @AuthenticationPrincipal UserPrincipal principal) {

        return responseBuilder.ok(orderService.getDraftOrder(new GetOrderCommand(principal.userId(), brandId)));
    }

    @Validated
    @GetMapping("/checkout")
    public ResponseEntity<ApiResponse<ViewCheckoutResponse>> getCheckoutDetails(
            @RequestParam @NotNull @Min(1) Long brandId,
            @AuthenticationPrincipal UserPrincipal principal) {

        CheckoutInfo checkoutInfo = orderService
                .getCheckoutDetails(GetCheckoutCommand.from(principal.userId(), brandId));

        return responseBuilder.ok(ViewCheckoutResponse.from(checkoutInfo));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<PaymentResponse>> checkoutOrder(
            @Valid @RequestBody CheckoutRequest request,
            @AuthenticationPrincipal UserPrincipal principal
            ) {
        PaymentInfo paymentInfo = orderService.checkoutOrder(request.toCommand(principal.userId()));

        return responseBuilder.ok(PaymentResponse.from(paymentInfo));
    }

    @PostMapping("/payment/webhook")
    public ResponseEntity<ApiResponse<Void>> validatePayment(
           @Valid @RequestBody PaymentResultRequest request
    ) {
        orderService.handlePaymentResult(request.toCommand());
        return responseBuilder.ok(null);
    }



}
