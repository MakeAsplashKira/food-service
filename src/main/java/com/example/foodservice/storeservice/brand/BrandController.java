package com.example.foodservice.storeservice.brand;


import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.storeservice.brand.dto.BrandInfo;
import com.example.foodservice.storeservice.brand.dto.RegisterBrandRequest;
import com.example.foodservice.storeservice.brand.dto.RegisterBrandResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/brand")
@RequiredArgsConstructor
public class BrandController {
    private final ResponseBuilder responseBuilder;
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegisterBrandResponse>> registerBrand(
            @Valid @RequestBody RegisterBrandRequest request) {

        BrandInfo brandInfo = brandService.registerBrand(request.toCommand());

        return responseBuilder.created(RegisterBrandResponse.from(brandInfo));
    }
}
