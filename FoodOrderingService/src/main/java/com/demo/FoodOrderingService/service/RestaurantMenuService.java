package com.demo.FoodOrderingService.service;

import com.demo.FoodOrderingService.model.RestaurantMenuItem;

public interface RestaurantMenuService {
    public RestaurantMenuItem getMenuItemByNameAndId(Long restaurantId, String menuItemName);

    public Double getPriceByNameAndId(Long restaurantId, String name);
}
