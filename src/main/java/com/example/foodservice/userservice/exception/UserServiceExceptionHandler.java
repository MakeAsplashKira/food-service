package com.example.foodservice.userservice.exception;


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
    public ResponseEntity<ApiResponse<Void>> handleNumberExist(NumberAlreadyTakenException e) {
        return responseBuilder.conflict(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundByNumberAndPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundByNumberAndPassword(UserNotFoundByNumberAndPasswordException e) {
        return responseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException e) {
        return responseBuilder.notFound(e.getMessage());
    }

}
