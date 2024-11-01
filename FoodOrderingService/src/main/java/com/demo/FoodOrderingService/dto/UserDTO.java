package com.demo.FoodOrderingService.dto;

import com.demo.FoodOrderingService.enums.UserRole;
import com.demo.FoodOrderingService.model.Order;
import com.demo.FoodOrderingService.model.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    private UserRole role;

//    private List<Order> orders;

//    private List<RestaurantDTO> favorites;

//    private Restaurant restaurant;
}
