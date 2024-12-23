package com.demo.FoodOrderingService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int orderQuantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_menu_food_id", nullable = false)
    private RestaurantMenuItem restaurantMenuItem;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name= "order_id", nullable = false)
    private Order order;

    private Double totalPrice;
}
