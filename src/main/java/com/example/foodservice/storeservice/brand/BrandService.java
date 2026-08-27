package com.example.foodservice.storeservice.brand;

import com.example.foodservice.storeservice.brand.dto.BrandInfo;
import com.example.foodservice.storeservice.brand.dto.RegisterBrandCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final BrandRepository brandRepository;

    private static final String IMAGE_URL = "/brand/bebend"; //TODO: сделать работу с файлами

    public BrandInfo registerBrand(RegisterBrandCommand command) {
        Brand brand = command.toBrand();

        brand.setImageUrl(IMAGE_URL);
        brand.setPasswordHash(passwordEncoder.encode(command.rawPassword()));

        brandRepository.save(brand);

        return BrandInfo.from(brand);
    }
}
