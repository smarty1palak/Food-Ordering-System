package com.demo.FoodOrderingService.service.strategy;

import com.demo.FoodOrderingService.dto.OrderItemDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HigherRatingStrategy implements RestaurantSelectionStrategy{
    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurants, List<OrderItemDTO> menuItems){
        Restaurant selectedRestaurant = null;
        double highestRating = Double.MIN_VALUE;

        for (Restaurant restaurant : restaurants) {
            if (canFulfillOrder(restaurant, menuItems)) {
                if (restaurant.getRating()>highestRating) {
                    highestRating = restaurant.getRating();
                    selectedRestaurant = restaurant;
                }
            }
        }
        return selectedRestaurant;
    }

    private boolean canFulfillOrder(Restaurant restaurant, List<OrderItemDTO> items) {
        int totalQuantity = 0;
        for(OrderItemDTO menuItem: items){
            totalQuantity+=menuItem.getQuantity();
        }
        return restaurant.getCurrLoad() + totalQuantity <= restaurant.getProcessingCapacity();
    }
}