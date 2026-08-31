package com.example.foodservice.brandservice.exception;

import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BrandExceptionHandler {
    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(BrandNameExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBrandNameExists(BrandNameExistsException e) {
        return responseBuilder.conflict(e.getMessage());
    }
    @ExceptionHandler(BrandEmailExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBrandEmailExists(BrandEmailExistsException e) {
        return responseBuilder.conflict(e.getEmail());
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBrandNotFound(BrandNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBrandAlreadyExists(BrandAlreadyExistsException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(SomeProductsNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSomeProductsNotFound(SomeProductsNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

}
