package com.example.foodservice.UserService.exception;


import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class UserServiceExceptionHandler {
    private final ResponseBuilder responseBuilder;

    @ExceptionHandler(NumberAlreadyTakenException.class)
    public ResponseEntity<ApiResponse> handleNumberExist(NumberAlreadyTakenException e) {
        return responseBuilder.conflict(e.getMessage());
    }

}
