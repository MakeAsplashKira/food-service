package com.example.foodservice.brandservice.dto;

import com.example.foodservice.brandservice.entity.Brand;
import com.example.foodservice.brandservice.entity.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public final class ProductDTO {
    private ProductDTO() {}

    public record AddProductCommand(
            Long brandId,
            String externalProductId,
            String name,
            String category,
            MultipartFile image

    ) {
        public Product toProductWithBrand(Brand brand) {
            return new Product(
                    brand,
                    this.externalProductId,
                    this.name,
                    this.category
            );

        }
    }
    public record AddProductRequest(
            @NotBlank String externalProductId,
            @NotBlank String name,
            @NotBlank String category

    ) {
        public AddProductCommand toCommand(Long brandId, MultipartFile image) {
            return new AddProductCommand(
                    brandId,
                    this.externalProductId,
                    this.name,
                    this.category,
                    image
            );
        }
    }

    public record AddProductResponse(
            Long id,
            String name,
            String category,
            String imageUrl
    ) {
        public static AddProductResponse from(ProductInfo productInfo) {
            return new AddProductResponse(
                    productInfo.id(),
                    productInfo.name(),
                    productInfo.category(),
                    productInfo.imageUrl()
            );
        }
    }

    public  record DeleteProductCommand(Long brandId, Long productId) {
        public static DeleteProductCommand from(Long brandId, Long productId) {
            return new DeleteProductCommand(
                    brandId,
                    productId
            );
        }
    }


}
