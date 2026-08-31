package com.example.foodservice.brandservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class StoreDTO {
    private StoreDTO() {
    }

    public record StoreInfo(
            Long id,
            String email,
            String address
    ) {

    }

    public record AddStoreCommand(Long brandId, String email, String rawPassword, String address) {

    }

    public record AddStoreRequest(@NotBlank @Email String email, @NotBlank @Size(min = 8, max = 64) String password,
                                  @NotBlank String address) {
        public AddStoreCommand toCommand(Long brandId) {
            return new AddStoreCommand(brandId, this.email, this.password, this.address);
        }
    }

    public record AddStoreResponse(
            Long id,
            String email,
            String address
    ) {
        public static AddStoreResponse from (StoreInfo storeInfo) {
            return new AddStoreResponse(
                    storeInfo.id(),
                    storeInfo.email(),
                    storeInfo.address()
            );
        }
    }

}
