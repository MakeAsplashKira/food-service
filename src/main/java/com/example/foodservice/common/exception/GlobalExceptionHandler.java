package com.example.foodservice.common.exception;

import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        HashMap<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return responseBuilder.badRequest(errors.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return responseBuilder.badRequest(e.getMessage());
    }
}
