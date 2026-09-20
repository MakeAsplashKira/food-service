package com.example.foodservice.orderservice;

import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.security.principal.StorePrincipal;
import com.example.foodservice.orderservice.dto.OrderStatusDTO.OrderStatusChangeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreOrderController {
    private final OrderService orderService;
    private final ResponseBuilder responseBuilder;

    @PatchMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<Void>> changeOrderStatus(
            @Valid @RequestBody OrderStatusChangeRequest request,
            @PathVariable @NotNull @Min(1) Long orderId,
            @AuthenticationPrincipal StorePrincipal storePrincipal) {

        orderService.handleOrderNewStatus(request.toCommand(storePrincipal.storeId(), orderId));

        return responseBuilder.ok(null);
    }
}
