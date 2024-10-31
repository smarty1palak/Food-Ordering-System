package com.demo.FoodOrderingService.repository;

import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

    @Query(value = "SELECT * from restaurant_menu_item r where r.restaurant_id = :id and r.item_name = :name", nativeQuery = true)
    Optional<RestaurantMenuItem> findByName(@Param("id") Long restaurantId, @Param("name") String name);

    @Query(value = "SELECT price from RestaurantMenuItem r where r.restaurant_id = :id and r.item_name = :name", nativeQuery = true)
    Double findPriceByNameAndId(@Param("name") String name, @Param("id") Long restaurantId);
}
