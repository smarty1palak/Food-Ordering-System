package com.demo.FoodOrderingService.service.strategy;

import com.demo.FoodOrderingService.dto.OrderItemDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.service.RestaurantMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class LowerCostStrategy implements RestaurantSelectionStrategy{

    @Autowired
    RestaurantMenuItemService restaurantMenuItemService;

    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurants, List<OrderItemDTO> menuItems){
        Restaurant selectedRestaurant = null;
        double lowestTotalCost = Double.MAX_VALUE;

        for (Restaurant restaurant : restaurants) {
            if (canFulfillOrder(restaurant, menuItems)) {
                System.out.println(restaurant.getName());
                double totalCost = calculateTotalCost(restaurant, menuItems);
                if (totalCost < lowestTotalCost) {
                    lowestTotalCost = totalCost;
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

    private double calculateTotalCost(Restaurant restaurant, List<OrderItemDTO> items) {
        double totalCost = 0;
        for (OrderItemDTO item : items) {
            totalCost +=restaurantMenuItemService.getPriceByNameAndId(restaurant.getId(),item.getItemName());
        }
        System.out.println(totalCost);
        return totalCost;
    }
}
