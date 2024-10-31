package com.demo.FoodOrderingService.config;

import com.demo.FoodOrderingService.service.strategy.HigherRatingStrategy;
import com.demo.FoodOrderingService.service.strategy.LowerCostStrategy;
import com.demo.FoodOrderingService.service.strategy.RestaurantSelectionStrategy;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {

    @Autowired
    private StrategyProperties strategyProperties;

    @Bean
    public RestaurantSelectionStrategy restaurantSelectionStrategy() {
        switch (strategyProperties.getSelection()) {
            case "lowerCost":
                return new LowerCostStrategy();
            case "higherRating":
                return new HigherRatingStrategy();
            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategyProperties.getSelection());
        }
    }
}
