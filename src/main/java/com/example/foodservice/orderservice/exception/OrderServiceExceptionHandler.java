package com.example.foodservice.orderservice.exception;


import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return responseBuilder.badRequest(e.getMessage());
    }

    @ExceptionHandler(BrandOrProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBrandOrProductNotFound(BrandOrProductNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderNotFound(OrderNotFoundException e) {
        return responseBuilder.badRequest(e.getMessage());
    }

}
