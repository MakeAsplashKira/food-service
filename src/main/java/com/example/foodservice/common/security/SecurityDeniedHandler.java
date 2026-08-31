package com.example.foodservice.common.security;

import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityDeniedHandler implements AccessDeniedHandler {
    private final ResponseBuilder responseBuilder;
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ResponseEntity<ApiResponse<Void>> apiResponse = responseBuilder.forbidden("Access denied");
        response.setStatus(apiResponse.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse.getBody()));

    }
}
