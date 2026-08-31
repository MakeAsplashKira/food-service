package com.example.foodservice.brandservice;

import static com.example.foodservice.brandservice.dto.AuthDTO.RegisterCommand;
import static com.example.foodservice.brandservice.dto.AuthDTO.LoginCommand;

import com.example.foodservice.brandservice.dto.BrandInfo;
import com.example.foodservice.brandservice.dto.StoreDTO.AddStoreCommand;
import com.example.foodservice.brandservice.dto.StoreDTO.StoreInfo;
import com.example.foodservice.brandservice.dto.product.AddProductCommand;
import com.example.foodservice.brandservice.dto.product.ProductInfo;
import com.example.foodservice.brandservice.entity.Brand;
import com.example.foodservice.brandservice.exception.BrandAlreadyExistsException;
import com.example.foodservice.brandservice.exception.BrandEmailExistsException;
import com.example.foodservice.brandservice.exception.BrandNameExistsException;
import com.example.foodservice.brandservice.exception.BrandNotFoundException;
import com.example.foodservice.brandservice.repository.BrandRepository;
import com.example.foodservice.common.exception.InvalidCredentialsException;
import com.example.foodservice.common.fileservice.FileStorageService;
import com.example.foodservice.common.security.JwtService;
import com.example.foodservice.common.security.SubjectType;
import com.example.foodservice.brandservice.entity.Product;
import com.example.foodservice.storeservice.StoreService;
import com.example.foodservice.brandservice.repository.ProductRepository;
import com.example.foodservice.storeservice.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FileStorageService storageService;

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final StoreService storeService;

    private static final String IMAGE_URL = "/brand/bebend"; //TODO: сделать работу с файлами

    @Transactional
    public String register(RegisterCommand command, MultipartFile image) {
        if(brandRepository.existsByName(command.name())) {
            throw new BrandNameExistsException(command.name());
        }
        if(brandRepository.existsByEmail(command.email())) {
            throw new BrandEmailExistsException(command.email());
        }

        Brand brand = command.toBrand();
        //TODO: добавить обработку фотографии и ее получение в целом
        brand.setPasswordHash(passwordEncoder.encode(command.rawPassword()));

        if(!image.isEmpty()) {
            String imageUrl = storageService.storeFile(image, "brands");
            brand.setImageUrl(imageUrl);
        }

        try {
            brandRepository.saveAndFlush(brand);
        } catch (DataIntegrityViolationException e) {
            throw new BrandAlreadyExistsException(command.name(), command.email());
        }
        return jwtService.generateAccessToken(brand.getId(), SubjectType.BRAND);
    }

    @Transactional(readOnly = true)
    public String login(LoginCommand command) {
        Brand brand = brandRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(command.password(), brand.getPasswordHash())){
            throw new InvalidCredentialsException();
        }

        return jwtService.generateAccessToken(brand.getId(), SubjectType.BRAND);
    }

    @Transactional
    public ProductInfo addProduct(AddProductCommand command) {
        Brand brand = brandRepository.getReferenceById(command.brandId());
               // .orElseThrow(() -> new BrandNotFoundException(command.brandId())); Пока не уверен как красиво сделать

        Product product = command.toProductWithBrand(brand);
        product.setImageUrl(IMAGE_URL);

        productRepository.save(product);

        return ProductInfo.from(product);
    }

    @Transactional
    public StoreInfo registerStore(AddStoreCommand command) {
        if(!brandRepository.existsById(command.brandId())) {
            throw new BrandNotFoundException(command.brandId());
        }

        StoreInfo storeInfo = storeService.register(command.brandId(), command.email(), command.rawPassword(), command.address());

        return storeInfo;
    }

    @Transactional(readOnly = true)
    public List<BrandInfo> getAllBrands() {
        List<Brand> brands =  brandRepository.findAll();

        return brands.stream()
                .map(BrandInfo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StoreInfo> getBrandStores(Long brandId) {
        return storeService.getStoresByBrandId(brandId);
    }

    //TODO: потом изменится
    @Transactional(readOnly = true)
    public List<ProductInfo> getProductsByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
                .map(ProductInfo::from)
                .toList();
    }
}
