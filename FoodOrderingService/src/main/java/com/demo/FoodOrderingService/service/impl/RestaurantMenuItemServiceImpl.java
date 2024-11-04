package com.demo.FoodOrderingService.service.impl;

import com.demo.FoodOrderingService.dto.RestaurantMenuItemDTO;
import com.demo.FoodOrderingService.exception.BadRequestException;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.repository.RestaurantRepository;
import com.demo.FoodOrderingService.repository.UserRepository;
import com.demo.FoodOrderingService.service.RestaurantMenuItemService;
import com.demo.FoodOrderingService.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

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

    public RestaurantMenuItem createMenuItem(RestaurantMenuItemDTO restaurantMenuItem){
        if (restaurantMenuItem.getItemName() == null) {
            throw new BadRequestException("Restaurant Menu Item Name Required");
        }

        if (restaurantMenuItem.getOwnerEmail() == null) {
            throw new BadRequestException("Restaurant Owner Email Required");
        }
        Restaurant restaurant = restaurantService.findByNameAndEmail(restaurantMenuItem.getRestaurantName(), restaurantMenuItem.getOwnerEmail());
        RestaurantMenuItem restaurantMenuItemNew = new RestaurantMenuItem();

        restaurantMenuItemNew.setRestaurant(restaurant);
        restaurantMenuItemNew.setItemName(restaurantMenuItem.getItemName());
        restaurantMenuItemNew.setPrice(restaurantMenuItem.getPrice());
        restaurantMenuItemNew.setQuantity(restaurantMenuItem.getQuantity());

        return restaurantMenuItemRepository.save(restaurantMenuItemNew);
    }

    public Double getPriceByNameAndId(Long restaurantId, String name){
        return restaurantMenuItemRepository.findPriceByNameAndId(name, restaurantId);
    }

    @Override
    public RestaurantMenuItem findById(Long id) {

        Optional<RestaurantMenuItem> restaurantMenuItem = restaurantMenuItemRepository.findById(id);

        if (restaurantMenuItem.isEmpty()) {
            throw new BadRequestException("Can not Find Restaurant Menu Item With Id " + id);
        }

        return restaurantMenuItem.get();
    }

    @Override
    public List<RestaurantMenuItem> getAllRestaurantMenuItems() {
        return restaurantMenuItemRepository.findAll();
    }

    @Override
    public boolean deleteRestaurantMenuItem(Long id) throws Exception {

        RestaurantMenuItem menuItem = findById(id);
        menuItem.setRestaurant(null);

        restaurantMenuItemRepository.save(menuItem);

        return true;
    }
}
