package com.demo.FoodOrderingService.service;

import com.demo.FoodOrderingService.dto.OrderDTO;
import com.demo.FoodOrderingService.enums.OrderStatus;
import com.demo.FoodOrderingService.model.Order;
import com.demo.FoodOrderingService.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderDTO orderDTO, User user) throws Exception;

    public Order updateOrder(Long orderId, OrderStatus orderStatus) throws Exception;

    public boolean canselOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception ;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
