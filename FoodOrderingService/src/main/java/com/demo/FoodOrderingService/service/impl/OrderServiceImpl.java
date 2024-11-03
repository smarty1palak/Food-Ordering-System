package com.demo.FoodOrderingService.service.impl;

import com.demo.FoodOrderingService.dto.OrderDTO;
import com.demo.FoodOrderingService.dto.OrderItemDTO;
import com.demo.FoodOrderingService.enums.OrderStatus;
import com.demo.FoodOrderingService.exception.BadRequestException;
import com.demo.FoodOrderingService.model.Order;
import com.demo.FoodOrderingService.model.Restaurant;
import com.demo.FoodOrderingService.model.User;
import com.demo.FoodOrderingService.repository.OrderItemRepository;
import com.demo.FoodOrderingService.repository.OrderRepository;
import com.demo.FoodOrderingService.repository.RestaurantMenuItemRepository;
import com.demo.FoodOrderingService.repository.UserRepository;
import com.demo.FoodOrderingService.model.*;
import com.demo.FoodOrderingService.service.OrderService;
import com.demo.FoodOrderingService.service.RestaurantMenuItemService;
import com.demo.FoodOrderingService.service.RestaurantService;
import com.demo.FoodOrderingService.service.strategy.RestaurantSelectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMenuItemService restaurantMenuItemService;

    @Autowired
    private RestaurantSelectionStrategy selectionStrategy;

    @Autowired
    private RestaurantMenuItemRepository restaurantMenuItemRepository;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    @Override
    public Order createOrder(OrderDTO orderDTO, User user) throws Exception {
        Order order = new Order();
        List<Restaurant> eligibleRestaurants = new ArrayList<>();
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurant();

        for (Restaurant restaurant : allRestaurants) {
            if (canFulfillOrder(restaurant, orderDTO.getItems())) {
                eligibleRestaurants.add(restaurant);
            }
        }

        Restaurant selectedRestaurant = selectionStrategy.selectRestaurant(eligibleRestaurants, orderDTO.getItems());
        // Process the order based on selected restaurant
        double totalCost = 0;
        int totalQuantity = 0;
        if (selectedRestaurant != null) {
            for(OrderItemDTO item : orderDTO.getItems()){
                double itemPrice = restaurantMenuItemService.getPriceByNameAndId(selectedRestaurant.getId(), item.getItemName());
                totalCost += itemPrice*item.getQuantity();
                totalQuantity += item.getQuantity();
            }
            selectedRestaurant.incrementProcessingLoad(totalQuantity);
        }
        else{
            throw new RuntimeException("No available restaurant can fulfill this order.");
        }

        order.setCustomer(user);
        order.setRestaurant(selectedRestaurant);
        order.setTotalAmount(totalCost);
        order.setOrderStatus(OrderStatus.PROCESSING);
        scheduleCapacityRelease(selectedRestaurant, totalQuantity, order, selectedRestaurant.getProcessingTimePerItem()*totalQuantity);
        orderRepository.save(order);

        for(OrderItemDTO item : orderDTO.getItems()){
            double itemPrice = restaurantMenuItemService.getPriceByNameAndId(selectedRestaurant.getId(), item.getItemName());
            RestaurantMenuItem restaurantMenuItem1 = restaurantMenuItemService.getMenuItemByNameAndId(selectedRestaurant.getId(), item.getItemName());
            restaurantMenuItem1.setQuantity(restaurantMenuItem1.getQuantity()-item.getQuantity());
            restaurantMenuItemRepository.save(restaurantMenuItem1);
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setTotalPrice(itemPrice*item.getQuantity());
            orderItem1.setOrderQuantity(item.getQuantity());
            orderItem1.setOrder(order);
            orderItem1.setRestaurantMenuItem(restaurantMenuItem1);
            orderItemRepository.save(orderItem1);
        }
        return order;
    }

    private boolean canFulfillOrder(Restaurant restaurant, List<OrderItemDTO> items){
        for (OrderItemDTO itemRequest : items) {
            RestaurantMenuItem menuItem = restaurantMenuItemService.getMenuItemByNameAndId(restaurant.getId(),itemRequest.getItemName());
            if (menuItem == null || menuItem.getQuantity() < itemRequest.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void scheduleCapacityRelease(Restaurant restaurant, int quantity, Order order, long delayInSeconds) {
        scheduler.schedule(() -> {
            System.out.println("Inside scheduler");
            System.out.println(order.getId());
            restaurant.releaseCapacity(quantity);
            restaurantService.updateRestaurant(restaurant.getId(),restaurant); // Update restaurant capacity

            try {
                updateOrder(order.getId(), OrderStatus.FULFILLED);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Capacity released for restaurant: " + restaurant.getId());
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    private synchronized void checkAndUpdateOrderStatus(Order order) {
        System.out.println("Order is fulfilled");
        System.out.println(order.getId());
        System.out.println(order.getOrderStatus());
        order.setOrderStatus(OrderStatus.FULFILLED);
        orderRepository.save(order); // Save the updated order status
    }

    @Override
    public Order updateOrder(Long orderId, OrderStatus orderStatus) throws Exception{

        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public boolean cancelOrder(Long orderId) throws Exception {

        Order order = findOrderById(orderId);

        if (order != null) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
       List<Order> orders =  orderRepository.findByRestaurantId(restaurantId);

       if (orderStatus != null) {
           orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
       }

       return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new BadRequestException("Order Not Found");
        }

        return optionalOrder.get();
    }
}
