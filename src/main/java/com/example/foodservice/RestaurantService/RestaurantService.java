package com.example.foodservice.RestaurantService;

import com.example.foodservice.OrderService.dto.OrderLine;
import com.example.foodservice.RestaurantService.dto.AddMenuItemRequest;
import com.example.foodservice.RestaurantService.dto.MenuItemRequestedQuantity;
import com.example.foodservice.RestaurantService.exception.*;
import com.example.foodservice.RestaurantService.dto.MenuItemInfo;
import com.example.foodservice.RestaurantService.dto.RegisterRequest;
import com.example.foodservice.RestaurantService.entity.MenuItem;
import com.example.foodservice.RestaurantService.entity.Restaurant;
import com.example.foodservice.RestaurantService.repository.MenuItemRepository;
import com.example.foodservice.RestaurantService.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;


@Service
@RequiredArgsConstructor
public class RestaurantService {
    final private RestaurantRepository restaurantRepository;
    final private MenuItemRepository menuItemRepository;
    final private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public String register(RegisterRequest request) {

        if(restaurantRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyTakenException(request.email());
        }

        Restaurant restaurant = new Restaurant(
                request.name(),
                request.email(),
                request.address()
        );

        restaurant.setPasswordHash(passwordEncoder.encode(request.password()));
        restaurant.setApiKey(generateApiKey());

        restaurantRepository.save(restaurant);

        return restaurant.getApiKey();
    }

    @Transactional
    public MenuItem addMenuItem(Long restaurantId, String apiKey, AddMenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findByApiKeyAndId(apiKey, restaurantId)
                .orElseThrow(() -> new NoSuchRestaurantException(restaurantId));

        MenuItem menuItem = new MenuItem(
                request.providerMenuItemId(),
                request.name(),
                request.price(),
                request.category(),
                restaurant,
                request.availableQuantity()
        );

        menuItemRepository.save(menuItem);

        return menuItem;
    }

    @Transactional
    public void deleteMenuItem(String apiKey, Long restaurantId, Long menuItemId) {
        restaurantRepository.findByApiKeyAndId(apiKey, restaurantId)
                .orElseThrow(() -> new NoSuchRestaurantException(restaurantId));

        menuItemRepository.findByIdAndRestaurantId(menuItemId, restaurantId)
                .orElseThrow(NoSuchMenuItemException::new);

        menuItemRepository.deleteById(menuItemId);
    }

    @Transactional(readOnly = true)
    public List<MenuItemInfo> getMenuItemsByIds(List<Long> menuItemsIds) {

        List<MenuItem> menuItems = menuItemRepository.findAllByIdWithRestaurant(menuItemsIds);

        if(menuItemsIds.size() != menuItems.size()) {
            throw new SomeMenuItemsMissingException();
        }

        return menuItems.stream()
                .map(MenuItemInfo::from)
                .toList();
    }

    @Transactional
    public void decreaseMenuItemQuantity(List<OrderLine> lines) {
       List<OrderLine> sortedLines = lines.stream() // одинаковый порядок сортировки (от дедлоков)
               .sorted(Comparator.comparing(OrderLine::menuItemId))
               .toList();

        for(OrderLine line : sortedLines) {
            int rowsAffected = menuItemRepository.decreaseQuantity(line.menuItemId(), line.quantity());
            if(rowsAffected == 0) {
                throw new NotEnoughMenuItemQuantityException(line.menuItemId(), line.quantity());
            }
        }
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
