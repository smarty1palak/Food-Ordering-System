package com.demo.FoodOrderingService.service.impl;

import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantMenuServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantMenuItemRepository restaurantMenuItemRepository;

    public Optional<RestaurantMenuItem> getMenuItemByNameAndId(Long restaurantId, String name) {
        return restaurantMenuItemRepository.findByName(restaurantId, name);
    }

    public Double getPriceByNameAndId(Long restaurantId, String name){
        return restaurantMenuItemRepository.findPriceByNameAndId(name, restaurantId);
    }
}
