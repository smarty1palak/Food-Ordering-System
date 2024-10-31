package com.mahel.FoodOrderingService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // quantity fulfilled by this restaurant
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "restaurant_menu_food_id", nullable = false)
    private RestaurantMenuFood restaurantMenuFood;

    @ManyToOne
    @JoinColumn(name= "order_id")
    private Order order;

//    private Long totalPrice;
//
//    private List<String> ingredients;
}
