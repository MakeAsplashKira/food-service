package com.example.foodservice.StoreService;

import com.example.foodservice.OrderService.dto.OrderLine;
import com.example.foodservice.StoreService.dto.AddMenuItemRequest;
import com.example.foodservice.StoreService.exception.*;
import com.example.foodservice.StoreService.dto.MenuItemInfo;
import com.example.foodservice.StoreService.dto.RegisterRequest;
import com.example.foodservice.StoreService.entity.Product;
import com.example.foodservice.StoreService.entity.Store;
import com.example.foodservice.StoreService.repository.ProductRepository;
import com.example.foodservice.StoreService.repository.StoreRepository;
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
    public Product addMenuItem(Long restaurantId, String apiKey, AddMenuItemRequest request) {
        Store store = storeRepository.findByApiKeyAndId(apiKey, restaurantId)
                .orElseThrow(() -> new NoSuchRestaurantException(restaurantId));

        Product product = new Product(
                request.providerMenuItemId(),
                request.name(),
                request.price(),
                request.category(),
                store,
                request.availableQuantity()
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
    public List<MenuItemInfo> getMenuItemsByIds(List<Long> menuItemsIds) {

        List<Product> products = productRepository.findAllByIdWithStore(menuItemsIds);

        if(menuItemsIds.size() != products.size()) {
            throw new SomeMenuItemsMissingException();
        }

        return products.stream()
                .map(MenuItemInfo::from)
                .toList();
    }

    @Transactional
    public void decreaseMenuItemQuantity(List<OrderLine> lines) {
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

    @Transactional(readOnly = true) // лучше сделать отдельную дто, чтобы отдавать только нужные поля...
    public MenuItemInfo getMenuItemByIdAndRestaurantId(Long menuItemId, Long restaurantId) {
       Product product =  productRepository.findByIdAndStoreId(menuItemId, restaurantId)
                .orElseThrow(NoSuchMenuItemException::new);

       return MenuItemInfo.from(product);
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
