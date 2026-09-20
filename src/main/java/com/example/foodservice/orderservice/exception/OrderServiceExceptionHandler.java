package com.example.foodservice.orderservice.exception;


import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class OrderServiceExceptionHandler {
    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(DuplicateMenuItemException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateMenuItem(DuplicateMenuItemException e) {
        return responseBuilder.badRequest(e.getMessage());
    }


    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderItemNotFound(OrderItemNotFoundException e) {
        return responseBuilder.badRequest(e.getMessage());
    }

    @ExceptionHandler(IllegalQuantityStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalQuantityState(IllegalQuantityStateException e) {
        return responseBuilder.badRequest(e.getMessage());
    }

    @ExceptionHandler(OrderUnmodifiableException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderUnmodifiable(OrderUnmodifiableException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(BrandOrProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBrandOrProductNotFound(BrandOrProductNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderNotFound(OrderNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(OrderItemUnavailableException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderItemUnavailable(OrderItemUnavailableException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(OrderStatusNotAllowPaymentException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderStatusNotAllowPayment(OrderStatusNotAllowPaymentException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(OrderAlreadyHasPaymentIdException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderAlreadyHasPaymentId(OrderAlreadyHasPaymentIdException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundByPaymentIdException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderNotFoundByPaymentIdException(OrderNotFoundByPaymentIdException e) {
        log.error(e.getMessage());
        return responseBuilder.notFound(null);
    }

    @ExceptionHandler(OrderStatusNotAllowCompletePaymentException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderStatusNotAllowCompletePayment(OrderStatusNotAllowCompletePaymentException e) {
        log.error(e.getMessage());
        return responseBuilder.ok(null);
    }

    @ExceptionHandler(PaymentGatewayException.class)
    public ResponseEntity<ApiResponse<Void>> handlePaymentGateway(PaymentGatewayException e) {
        log.warn(e.getMessage());
        return responseBuilder.ok(null);
    }

    @ExceptionHandler(OrderNotFoundByStoreException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderNotFoundByStore(OrderNotFoundByStoreException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(OrderStatusIllegalTransitionException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderStatusIllegalTransition(OrderStatusIllegalTransitionException e) {
        return responseBuilder.conflict(e.getMessage());
    }
}
