package com.demo.FoodOrderingService.service.strategy;

import com.demo.FoodOrderingService.dto.OrderItemDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;

import java.util.List;

public interface RestaurantSelectionStrategy {
    Restaurant selectRestaurant(List<Restaurant> restaurants, List<OrderItemDTO> menuItems);
}
