package com.example.foodservice.storeservice.controller;

import com.example.foodservice.common.security.principal.StorePrincipal;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.storeservice.StoreService;
import com.example.foodservice.storeservice.dto.AuthDTO.AuthResponse;
import com.example.foodservice.storeservice.dto.AuthDTO.LoginRequest;
import com.example.foodservice.storeservice.dto.ProductDTO.ProductInfo;
import com.example.foodservice.storeservice.dto.StockDTO;
import com.example.foodservice.storeservice.dto.StockDTO.AddStockResponse;
import com.example.foodservice.storeservice.dto.StockDTO.StockInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final ResponseBuilder responseBuilder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        String token = storeService.login(request.toCommand());

        return responseBuilder.ok(new AuthResponse(token));
    }

    //потом тут будет register

    @PostMapping("/stock")
    public ResponseEntity<ApiResponse<AddStockResponse>> addStock(
            @Valid @RequestBody StockDTO.AddStockRequest request,
            @RequestParam Long productId,
            @AuthenticationPrincipal StorePrincipal storePrincipal) {

        StockInfo productInfo = storeService.addStock(request.toCommand(storePrincipal.storeId(), productId));

        return responseBuilder.created(AddStockResponse.from(productInfo));
    }

}
