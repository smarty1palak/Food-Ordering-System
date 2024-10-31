package com.demo.FoodOrderingService.service.strategy;

import com.demo.FoodOrderingService.dto.OrderItemDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class LowerCostStrategy implements RestaurantSelectionStrategy{
    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurants, List<OrderItemDTO> menuItems){
        Restaurant selectedRestaurant = null;
        double lowestTotalCost = Double.MAX_VALUE;

        for (Restaurant restaurant : restaurants) {
            if (canFulfillOrder(restaurant, menuItems)) {
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
            int itemIndex = restaurant.getMenu().indexOf(item);
            if (itemIndex != -1) {
                totalCost += restaurant.getMenu().get(itemIndex).getPrice();
            }
        }
        return totalCost;
    }
}
