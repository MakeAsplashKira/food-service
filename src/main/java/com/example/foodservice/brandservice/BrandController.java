package com.example.foodservice.brandservice;

import com.example.foodservice.brandservice.dto.AuthDTO.*;
import com.example.foodservice.brandservice.dto.BrandInfo;
import com.example.foodservice.brandservice.dto.StoreDTO.AddStoreRequest;
import com.example.foodservice.brandservice.dto.StoreDTO.AddStoreResponse;
import com.example.foodservice.brandservice.dto.StoreDTO.StoreInfo;
import com.example.foodservice.brandservice.dto.product.AddProductRequest;
import com.example.foodservice.brandservice.dto.product.AddProductResponse;
import com.example.foodservice.brandservice.dto.product.ProductInfo;
import com.example.foodservice.brandservice.dto.product.ProductResponse;
import com.example.foodservice.common.ResponseBuilder;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.security.principal.BrandPrincipal;
import com.example.foodservice.storeservice.entity.Store;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@RequiredArgsConstructor
public class BrandController {
    private final ResponseBuilder responseBuilder;
    private final BrandService brandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AuthResponse>> register
            (@Valid @RequestPart("request") RegisterRequest request,
             @RequestPart(value = "image") MultipartFile image) {

        String token = brandService.register(request.toCommand(), image);

        return responseBuilder.created(AuthResponse.from(token));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        String token = brandService.login(request.toCommand());
        return responseBuilder.ok(AuthResponse.from(token));
    }

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<AddProductResponse>> addProduct(
            @Valid @RequestBody AddProductRequest request,
            @AuthenticationPrincipal BrandPrincipal principal) {

        ProductInfo productInfo = brandService.addProduct(request.toCommand(principal.brandId()));

        return responseBuilder.created(AddProductResponse.from(productInfo));
    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponse<AddStoreResponse>> registerStore(
            @Valid @RequestBody AddStoreRequest request,
            @AuthenticationPrincipal BrandPrincipal principal) {

        StoreInfo storeInfo = brandService.registerStore(request.toCommand(principal.brandId()));

        return responseBuilder.created(AddStoreResponse.from(storeInfo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandInfo>>> getAllBrands() {

        List<BrandInfo> brandInfo = brandService.getAllBrands();
        return responseBuilder.ok(brandInfo);
    }

    @GetMapping("/stores")
    public ResponseEntity<ApiResponse<List<StoreInfo>>> getBrandStores(
            @AuthenticationPrincipal BrandPrincipal principal
    ) {
        List<StoreInfo> stores = brandService.getBrandStores(principal.brandId());

        return responseBuilder.ok(stores);
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(
            @AuthenticationPrincipal BrandPrincipal brandPrincipal) {
        List<ProductInfo> products = brandService.getProductsByBrandId(brandPrincipal.brandId());
        return responseBuilder.ok(products.stream().map(ProductResponse::from).toList());
    }
}
