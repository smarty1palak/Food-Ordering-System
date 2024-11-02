package com.demo.FoodOrderingService.service;

import com.demo.FoodOrderingService.dto.RestaurantMenuItemDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;

public interface RestaurantMenuItemService {
    public RestaurantMenuItem getMenuItemByNameAndId(Long restaurantId, String menuItemName);

    public Double getPriceByNameAndId(Long restaurantId, String name);

    public RestaurantMenuItem createMenuItem(RestaurantMenuItemDTO restaurantMenuItemDTO);

    public RestaurantMenuItem findById(Long id);

    boolean deleteRestaurantMenuItem(Long id) throws Exception;
}
