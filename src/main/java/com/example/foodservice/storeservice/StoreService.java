package com.example.foodservice.storeservice;

import com.example.foodservice.OrderService.dto.OrderLine;
import com.example.foodservice.storeservice.dto.AddProductRequest;
import com.example.foodservice.storeservice.exception.*;
import com.example.foodservice.storeservice.dto.ProductInfo;
import com.example.foodservice.storeservice.dto.RegisterRequest;
import com.example.foodservice.storeservice.entity.Product;
import com.example.foodservice.storeservice.entity.Store;
import com.example.foodservice.storeservice.repository.ProductRepository;
import com.example.foodservice.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StoreService {
    final private StoreRepository storeRepository;
    final private ProductRepository productRepository;
    final private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public String register(RegisterRequest request) {

        if(storeRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyTakenException(request.email());
        }

        Store store = new Store(
                request.name(),
                request.email(),
                request.address()
        );

        store.setPasswordHash(passwordEncoder.encode(request.password()));
        store.setApiKey(generateApiKey());

        storeRepository.save(store);

        return store.getApiKey();
    }

    @Transactional
    public Product addProduct(Long restaurantId, String apiKey, AddProductRequest request) {
        Store store = storeRepository.findByApiKeyAndId(apiKey, restaurantId)
                .orElseThrow(() -> new NoSuchRestaurantException(restaurantId));

        Product product = new Product(
                request.providerMenuItemId().toString(),
                request.name(),
                request.category()
        );

        productRepository.save(product);

        return product;
    }

    @Transactional
    public void deleteMenuItem(String apiKey, Long restaurantId, Long menuItemId) {
        storeRepository.findByApiKeyAndId(apiKey, restaurantId)
                .orElseThrow(() -> new NoSuchRestaurantException(restaurantId));

        productRepository.findByIdAndStoreId(menuItemId, restaurantId)
                .orElseThrow(NoSuchMenuItemException::new);

        productRepository.deleteById(menuItemId);
    }

    @Transactional(readOnly = true)
    public List<ProductInfo> getProductsByIds(List<Long> menuItemsIds) {

        List<Product> products = productRepository.findAllByIdWithStore(menuItemsIds);

        if(menuItemsIds.size() != products.size()) {
            throw new SomeMenuItemsMissingException();
        }

        return products.stream()
                .map(ProductInfo::from)
                .toList();
    }

    @Transactional
    public void decreaseProductQuantity(List<OrderLine> lines) {
       List<OrderLine> sortedLines = lines.stream() // одинаковый порядок сортировки (от дедлоков)
               .sorted(Comparator.comparing(OrderLine::menuItemId))
               .toList();

        for(OrderLine line : sortedLines) {
            int rowsAffected = productRepository.decreaseQuantity(line.menuItemId(), line.quantity());
            if(rowsAffected == 0) {
                throw new NotEnoughMenuItemQuantityException(line.menuItemId(), line.quantity());
            }
        }
    }

    @Transactional(readOnly = true)
    public ProductInfo getProductByIdAndStoreId(Long menuItemId, Long restaurantId) {
       Product product =  productRepository.findByIdAndStoreId(menuItemId, restaurantId)
                .orElseThrow(NoSuchMenuItemException::new);

       return ProductInfo.from(product);
    }



    private boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bit
        random.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }



}
