package com.example.foodservice.storeservice;

import com.example.foodservice.storeservice.dto.*;
import com.example.foodservice.storeservice.entity.Product;
import com.example.foodservice.storeservice.dto.AddProductResponse;
import com.example.foodservice.common.exception.AuthRequiredException;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {

        String apiKey = storeService.register(request);

        return responseBuilder.created(new RegisterResponse(apiKey));

    }



    @DeleteMapping("/{storeId}/product/{productId}")
    public ResponseEntity<Void> deleteProduct(HttpServletRequest rawRequest,
                                                         @PathVariable Long storeId,
                                                         @PathVariable Long productId) {
        String apiKey = extractApiKey(rawRequest);

        storeService.deleteMenuItem(apiKey, storeId, productId);

        return responseBuilder.noContent();
    }



    private String extractApiKey(HttpServletRequest request) {
        String rawApiKey = request.getHeader("Authorization");

        if(isApiKeyExists(rawApiKey)) {
            throw new AuthRequiredException("Api key is required");
        }

        return rawApiKey.replace("Bearer ", "");
    }

    private boolean isApiKeyExists(String rawApiKey) {
        return rawApiKey == null || rawApiKey.isBlank() || !rawApiKey.startsWith("Bearer ");
    }
}
