package com.demo.FoodOrderingService.repository;

import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByOwnerId(Long userId);

    @Query(value = "SELECT * from Restaurant r where r.owner_id = :id and r.name = :name", nativeQuery = true)
    Restaurant findByNameAndOwnerId(@Param("name") String restaurantName, @Param("id") Long ownerId);
}
