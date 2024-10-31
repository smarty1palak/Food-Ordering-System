package com.demo.FoodOrderingService.dto;

import com.demo.FoodOrderingService.enums.UserRole;
import com.demo.FoodOrderingService.model.Order;
import com.demo.FoodOrderingService.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String fullName;

    private String email;

    private String password;

    private UserRole role;

    private List<Order> orders;

    private List<RestaurantDTO> favorites;

    private Restaurant restaurant;
}
