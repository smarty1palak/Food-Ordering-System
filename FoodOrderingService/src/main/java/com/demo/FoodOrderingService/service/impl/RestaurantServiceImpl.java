package com.demo.FoodOrderingService.service.impl;

import com.demo.FoodOrderingService.dto.RestaurantDTO;
import com.demo.FoodOrderingService.exception.BadRequestException;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.User;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.repository.RestaurantRepository;
import com.demo.FoodOrderingService.repository.UserRepository;
import com.demo.FoodOrderingService.service.RestaurantService;
import com.demo.FoodOrderingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantMenuItemRepository restaurantMenuItemRepository;

    @Override
    public Restaurant createRestaurant(RestaurantDTO restaurant) {

        if (restaurant.getName() == null) {
            throw new BadRequestException("Restaurant Name Required");
        }

        if (restaurant.getDescription() == null) {
            throw new BadRequestException("Restaurant Description Required");
        }
        User owner = userRepository.findByEmail(restaurant.getOwnerEmail());
        Restaurant restaurant1 = new Restaurant();

        restaurant1.setName(restaurant.getName());
        restaurant1.setDescription(restaurant.getDescription());
        restaurant1.setRating(restaurant.getRating());
        restaurant1.setProcessingCapacity(restaurant.getProcessingCapacity());
        restaurant1.setProcessingTimePerItem(restaurant.getProcessingTimePerItem());
        restaurant1.setOwner(owner);

        return restaurantRepository.save(restaurant1);
    }

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {

        Optional<Restaurant> existingRestaurantOpt = restaurantRepository.findById(id);

        if (existingRestaurantOpt.isEmpty()) {
            throw new BadRequestException("Can not find Restaurant");
        }

        Restaurant existingRestaurant = existingRestaurantOpt.get();

        if (restaurant.getName() != null) {
            existingRestaurant.setName(restaurant.getName());
        }
        if (restaurant.getDescription() != null) {
            existingRestaurant.setDescription(restaurant.getDescription());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Boolean deleteRestaurant(Long id) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant.isEmpty()) {
            throw new BadRequestException("Can not Find Restaurant With Id " + id);
        }

        restaurantRepository.delete(restaurant.get());

        return true;
    }

    @Override
    public Restaurant findByNameAndEmail(String restaurantName, String ownerEmail){
        User user = userRepository.findByEmail(ownerEmail);
        return restaurantRepository.findByNameAndOwnerId(restaurantName, user.getId());
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }



    @Override
    public Restaurant findById(Long id) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant.isEmpty()) {
            throw new BadRequestException("Can not Find Restaurant With Id " + id);
        }

        return restaurant.get();
    }

    @Override
    public List<Restaurant> findRestaurantByOwnerId(Long id) {

        List<Restaurant> restaurants = restaurantRepository.findByOwnerId(id);

        if (restaurants == null) {
            throw new BadRequestException("Restaurants Can not found By Owner Id " + id);
        }

        return restaurants;
    }
}
