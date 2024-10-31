package com.demo.FoodOrderingService.repository;

import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

    @Query("SELECT * from RestaurantMenuItem r where r.id == :restaurantId and r.name == :name")
    Optional<RestaurantMenuItem> findByName(Long restaurantId, String name);

    @Query("SELECT price from RestaurantMenuItem r where r.id == :restaurantId and r.name == :name")
    Double findPriceByNameAndId(String name, Long restaurantId);
}
