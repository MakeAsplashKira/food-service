package com.example.foodservice.storeservice.exception;


import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.exception.AuthRequiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO: посмотреть как делается в реальных проектах бигтеха
@RestControllerAdvice
@RequiredArgsConstructor
public class StoreServiceExceptionHandler {
    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(StoreExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreExists(StoreExistsException e) {
        return responseBuilder.badRequest(e.getMessage());
    }

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoApiKey(AuthRequiredException e) {
        return responseBuilder.unauthorized(e.getMessage());
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoSuchMenuItem(NoSuchProductException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(SomeMenuItemsMissingException.class)
    public ResponseEntity<ApiResponse<Void>> handleSomeMenuItemsMissing(SomeMenuItemsMissingException e) {
        return responseBuilder.badRequest(e.getMessage());
    }

    @ExceptionHandler(StockExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleStockAlreadyExists(StockExistsException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreNotFound(StoreNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(StoreNotFoundByBrandException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreNotFoundByBrand(StoreNotFoundByBrandException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(StoreNotEnoughStock.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreNotEnoughStock(StoreNotEnoughStock e) {
        return responseBuilder.conflict(e.getMessage());
    }
}
