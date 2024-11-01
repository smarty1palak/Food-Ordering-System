package com.demo.FoodOrderingService.controller;

import com.demo.FoodOrderingService.dto.RestaurantDTO;
import com.demo.FoodOrderingService.dto.RestaurantMenuItemDTO;
import com.demo.FoodOrderingService.dto.response.ResponseDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.repository.RestaurantRepository;
import com.demo.FoodOrderingService.service.RestaurantMenuItemService;
import com.demo.FoodOrderingService.service.RestaurantService;
import com.demo.FoodOrderingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/menu-items")
public class RestaurantMenuItemController {

    @Autowired
    private RestaurantMenuItemService restaurantMenuItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantMenuItemRepository restaurantMenuItemRepository;

    @PostMapping("/register-item")
    public ResponseEntity<ResponseDTO<RestaurantMenuItemDTO>> registerMenuItems(@RequestBody RestaurantMenuItemDTO restaurantMenuItem) {
        ResponseDTO<RestaurantMenuItemDTO> response = new ResponseDTO<>();
        RestaurantMenuItem newRestaurantMenuItem = restaurantMenuItemService.createMenuItem(restaurantMenuItem);
        restaurantMenuItemRepository.save(newRestaurantMenuItem);
        response.setPayload(restaurantMenuItem);
        response.setMessage("Restaurant Menu Item created successfully");
        response.setHttpStatus(HttpStatus.CREATED);
        response.setCode("201");
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
