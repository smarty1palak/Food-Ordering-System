package com.demo.FoodOrderingService.service;

import com.demo.FoodOrderingService.dto.RestaurantDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.User;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(Restaurant restaurant, User user);

    public Restaurant updateRestaurant(Long id, Restaurant restaurant);

    public Boolean deleteRestaurant(Long id);

    public List<Restaurant> getAllRestaurant();

    public Restaurant updateStatus(Long id);

    public Restaurant findById(Long id);

    public RestaurantDTO addToFavorites(Long restaurantId, User user);

    public List<Restaurant> findRestaurantByOwnerId(Long id);
}
