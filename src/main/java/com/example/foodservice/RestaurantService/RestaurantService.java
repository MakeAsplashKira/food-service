package com.example.foodservice.RestaurantService;

import com.example.foodservice.RestaurantService.dto.AddMenuItemRequest;
import com.example.foodservice.common.dto.MenuItemResponse;
import com.example.foodservice.RestaurantService.dto.RegisterRequest;
import com.example.foodservice.RestaurantService.entity.MenuItem;
import com.example.foodservice.RestaurantService.entity.Restaurant;
import com.example.foodservice.RestaurantService.exception.DifferentRestaurantException;
import com.example.foodservice.RestaurantService.exception.EmailAlreadyTakenException;
import com.example.foodservice.RestaurantService.exception.NoSuchMenuItemException;
import com.example.foodservice.RestaurantService.exception.NoSuchRestaurantException;
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

    private static final int MAX_RESTAURANTS_AVAILABLE_FOR_ORDER = 1;

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

    @Transactional
    public List<MenuItemResponse> getMenuItemsByIds(Map<String, Integer> rawData) {
        Map<Long, Integer> data = new HashMap<>();
        rawData.forEach((key, value) -> data.put(Long.parseLong(key), value));
        // пока просто кидаю responseBilder.badRequest для IllegalArgumentException но само сообщение пока не передаю, наверное надо через try catch, не уверен

        List<MenuItem> menuItems = menuItemRepository.findAllByIdWithRestaurant(data.keySet());

        if(menuItems.size() < rawData.size()) {
            throw new NoSuchMenuItemException();
        }
        // добавить обработку availableQuantity ( что если нету товара ). Вообще не уверен как ресторан должен определять эти поля,
        // у них же не всегда будет написано у меня есть 100 шаурмы, это наверное их проблемы,
        // моя задача грамотно обрабатывать случаи если все же закончилось что-то в целом и провайдер сообщил об этом

        Set<Long> restaurantIds = new HashSet<>();
        menuItems.forEach(menuItem -> restaurantIds.add(menuItem.getRestaurant().getId())); // вроде как сейчас не должно быть N + 1
        if(restaurantIds.size() > MAX_RESTAURANTS_AVAILABLE_FOR_ORDER) {
            throw new DifferentRestaurantException();
        }

        return menuItems.stream()
                .map(MenuItemResponse::from)
                .toList();
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
