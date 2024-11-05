package com.demo.FoodOrderingService.repository;

import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RestaurantMenuItem r WHERE r.restaurant.id = :id AND r.itemName = :name")
    Optional<RestaurantMenuItem> findByName(@Param("id") Long restaurantId, @Param("name") String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r.price FROM RestaurantMenuItem r WHERE r.restaurant.id = :id AND r.itemName = :name")
    Double findPriceByNameAndId(@Param("name") String name, @Param("id") Long restaurantId);

}
