package com.demo.FoodOrderingService.controller;

import com.demo.FoodOrderingService.dto.RestaurantDTO;
import com.demo.FoodOrderingService.dto.response.ResponseDTO;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.repository.RestaurantRepository;
import com.demo.FoodOrderingService.repository.UserRepository;
import com.demo.FoodOrderingService.service.RestaurantService;
import com.demo.FoodOrderingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping()
    public ResponseEntity<ResponseDTO<List<Restaurant>>> getAllRestaurant() {

        ResponseDTO<List<Restaurant>> response = new ResponseDTO<>();
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();

        response.setPayload(restaurants);
        response.setMessage("Successful");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Restaurant>> findRestaurantById(@PathVariable Long id) {

        ResponseDTO<Restaurant> response = new ResponseDTO<>();
        Restaurant restaurant = restaurantService.findById(id);

        response.setPayload(restaurant);
        response.setMessage("Successful");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<RestaurantDTO>> registerRestaurant(@RequestBody RestaurantDTO restaurant) {
        ResponseDTO<RestaurantDTO> response = new ResponseDTO<>();
        Restaurant newRestaurant = restaurantService.createRestaurant(restaurant);
        restaurantRepository.save(newRestaurant);
        response.setPayload(restaurant);
        response.setMessage("Restaurant created successfully");
        response.setHttpStatus(HttpStatus.CREATED);
        response.setCode("201");
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteRestaurantById(@RequestParam Long id) throws Exception {

        ResponseDTO<Boolean> response = new ResponseDTO<>();
        boolean isDelete = restaurantService.deleteRestaurant(id);

        response.setPayload(isDelete);
        response.setMessage("Deleted Successfully");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
//
//    @PostMapping("/{id}/menu-items")
//    public Restaurant addMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
//        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found"));
//        menuItem.setRestaurant(restaurant);
//        restaurant.getMenuItems().add(menuItem);
//        return restaurantRepository.save(restaurant);
//    }

//    @PutMapping("/{id}/add-favorites")
//    public ResponseEntity<ResponseDTO<RestaurantDTO>> addToFavorites(
//            @PathVariable Long id,
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception {
//        ResponseDTO<RestaurantDTO> response = new ResponseDTO<>();
//        User user = userService.userByToken(jwt);
//        RestaurantDTO restaurant = restaurantService.addToFavorites(id, user);
//
//        response.setPayload(restaurant);
//        response.setMessage("Successful");
//        response.setHttpStatus(HttpStatus.OK);
//        response.setCode("200");
//
//        return new ResponseEntity<>(response, response.getHttpStatus());
//    }
}
