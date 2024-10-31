package com.demo.FoodOrderingService.controller;

import com.demo.FoodOrderingService.dto.OrderDTO;
import com.demo.FoodOrderingService.dto.response.ResponseDTO;
import com.demo.FoodOrderingService.model.Order;
import com.demo.FoodOrderingService.model.User;
import com.demo.FoodOrderingService.service.OrderService;
import com.demo.FoodOrderingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Order>> createOrder(
            @RequestBody OrderDTO orderDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        ResponseDTO<Order> response = new ResponseDTO<>();
        User user = userService.userByToken(jwt);

        Order order = orderService.createOrder(orderDTO, user);

        response.setPayload(order);
        response.setMessage("Success");
        response.setHttpStatus(HttpStatus.CREATED);
        response.setCode("201");

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDTO<List<Order>>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {

        ResponseDTO<List<Order>> response = new ResponseDTO<>();
        User user = userService.userByToken(jwt);

        List<Order> orders = orderService.getUserOrders(user.getId());

        response.setPayload(orders);
        response.setMessage("Success");
        response.setHttpStatus(HttpStatus.OK);
        response.setCode("200");

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

}
