package com.example.foodservice.OrderService.exception;


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

    @ExceptionHandler(DifferentRestaurantException.class)
    public ResponseEntity<ApiResponse<Void>> handleDifferentRestaurant(DifferentRestaurantException e) {
        return responseBuilder.badRequest(e.getMessage());
    }

}
