package com.demo.FoodOrderingService.controller;

import com.demo.FoodOrderingService.dto.RestaurantMenuItemDTO;
import com.demo.FoodOrderingService.dto.response.ResponseDTO;
import com.demo.FoodOrderingService.model.RestaurantMenuItem;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.service.RestaurantMenuItemService;
import com.demo.FoodOrderingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/menu-items")
public class RestaurantMenuItemController {

    @Autowired
    private RestaurantMenuItemService restaurantMenuItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantMenuItemRepository restaurantMenuItemRepository;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<RestaurantMenuItem>>> getAllRestaurantMenuItems() {

        ResponseDTO<List<RestaurantMenuItem>> response = new ResponseDTO<>();
        List<RestaurantMenuItem> restaurantMenuItems = restaurantMenuItemService.getAllRestaurantMenuItems();
        response.setPayload(restaurantMenuItems);
        response.setMessage("Successful");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<RestaurantMenuItem>> findRestaurantMenuItemById(@PathVariable Long id) {

        ResponseDTO<RestaurantMenuItem> response = new ResponseDTO<>();
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemService.findById(id);

        response.setPayload(restaurantMenuItem);
        response.setMessage("Successful");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteRestaurantMenuItemById(@RequestParam Long id) throws Exception {

        ResponseDTO<Boolean> response = new ResponseDTO<>();
        boolean isDelete = restaurantMenuItemService.deleteRestaurantMenuItem(id);

        response.setPayload(isDelete);
        response.setMessage("Deleted Successfully");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
