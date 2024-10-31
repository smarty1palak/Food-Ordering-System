package com.demo.FoodOrderingService.service.impl;

import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.repository.UserRepository;
import com.demo.FoodOrderingService.service.RestaurantMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantMenuServiceImpl implements RestaurantMenuService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantMenuItemRepository restaurantMenuItemRepository;

    public RestaurantMenuItem getMenuItemByNameAndId(Long restaurantId, String name) {
        Optional<RestaurantMenuItem>menuItem = restaurantMenuItemRepository.findByName(restaurantId, name);
        if(menuItem.isEmpty() || menuItem == null){
            return null;
        }
        else{
            return menuItem.get();
        }
    }

    public Double getPriceByNameAndId(Long restaurantId, String name){
        return restaurantMenuItemRepository.findPriceByNameAndId(name, restaurantId);
    }
}
