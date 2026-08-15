package com.example.foodservice.RestaurantService.exception;


import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.exception.AuthRequiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestaurantServiceExceptionHandler {
    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ApiResponse> handleEmailExist(EmailAlreadyTakenException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<ApiResponse> handleNoApiKey(AuthRequiredException e) {
        return responseBuilder.unauthorized(e.getMessage());
    }

    @ExceptionHandler(NoSuchRestaurantException.class)
    public ResponseEntity<ApiResponse> handleNoSuchRestaurant(NoSuchRestaurantException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(NoSuchMenuItemException.class)
    public ResponseEntity<ApiResponse> handleNoSuchMenuItem(NoSuchMenuItemException e) {
        return responseBuilder.notFound(e.getMessage());
    }
}
