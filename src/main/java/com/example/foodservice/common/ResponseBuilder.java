package com.example.foodservice.common;

import com.example.foodservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseBuilder {
    private final RequestMetrics requestMetrics;
    private final static boolean SUCCESS = true;
    private final static boolean FAILURE = false;
    private final static String NO_ERROR = null;
    private final static String NO_DATA = null;

    public <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(build(SUCCESS, data, NO_ERROR));
    }

    public <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(build(SUCCESS, data, NO_ERROR));
    }

    public ResponseEntity<ApiResponse> conflict(String error) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value())
                .body(build(FAILURE, NO_DATA, error));
    }

    public ResponseEntity<ApiResponse> unauthorized(String error) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .body(build(FAILURE, NO_DATA, error));
    }

    public ResponseEntity<ApiResponse> notFound(String error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(build(FAILURE, NO_DATA, error));
    }

    public ResponseEntity<ApiResponse> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value())
                .body(build(SUCCESS, NO_DATA, NO_ERROR));
    }

    private <T> ApiResponse<T> build(boolean success, T data, String error) {
        return new ApiResponse<>(
                success,
                data,
                error,
                requestMetrics.finishAndGetStats()
        );
    }

}
