package com.example.foodservice.RestaurantService;

import com.example.foodservice.RestaurantService.dto.*;
import com.example.foodservice.RestaurantService.entity.MenuItem;
import com.example.foodservice.common.exception.AuthRequiredException;
import com.example.foodservice.common.dto.ApiResponse;
import com.example.foodservice.common.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {

        String apiKey = restaurantService.register(request);

        return responseBuilder.created(new RegisterResponse(apiKey));

    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<ApiResponse<AddMenuItemResponse>> addMenuItem(
            HttpServletRequest rawRequest,
            @PathVariable Long id,
            @Valid @RequestBody AddMenuItemRequest request) {

        String apiKey = extractApiKey(rawRequest);

        MenuItem menuItem = restaurantService.addMenuItem(id, apiKey, request);

        return responseBuilder.created(new AddMenuItemResponse(
                menuItem.getId(),
                menuItem.getRestaurant().getId(),
                menuItem.getName(),
                menuItem.getPrice(),
                menuItem.getCategory()
        ));
    }

    @DeleteMapping("/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<ApiResponse> deleteMenuItem(HttpServletRequest rawRequest,
                                                         @PathVariable Long restaurantId,
                                                         @PathVariable Long menuItemId) {
        String apiKey = extractApiKey(rawRequest);

        restaurantService.deleteMenuItem(apiKey, restaurantId, menuItemId);

        return responseBuilder.noContent();
    }



    private String extractApiKey(HttpServletRequest request) {
        String rawApiKey = request.getHeader("Authorization");

        if(rawApiKey == null || rawApiKey.isBlank() || !rawApiKey.startsWith("Bearer ")) {
            throw new AuthRequiredException("Api key is required");
        }

        return rawApiKey.replace("Bearer ", "");
    }
}
