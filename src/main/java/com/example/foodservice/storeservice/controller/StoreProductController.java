package com.example.foodservice.storeservice.controller;

import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.storeservice.StoreService;
import com.example.foodservice.storeservice.dto.ProductDTO.ProductOfferInfo;
import com.example.foodservice.storeservice.dto.ProductDTO.ProductOfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@RequiredArgsConstructor
public class StoreProductController {
    private final StoreService storeService;
    private final ResponseBuilder responseBuilder;

    @GetMapping("/{brandId}/products")
    public ResponseEntity<ApiResponse<List<ProductOfferResponse>>> getBrandProductsOffers(
            @PathVariable Long brandId) {
        List<ProductOfferInfo> result = storeService.getBrandProductsOffers(brandId);

        return responseBuilder.ok(result.stream().map(ProductOfferResponse::from).toList());
    }
}
