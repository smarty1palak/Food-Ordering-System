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
import com.demo.FoodOrderingService.repository.UserRepository;
import com.demo.FoodOrderingService.model.*;
import com.demo.FoodOrderingService.service.OrderService;
import com.demo.FoodOrderingService.service.RestaurantMenuService;
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
    private RestaurantMenuService restaurantMenuService;

    @Autowired
    private RestaurantSelectionStrategy selectionStrategy;


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
                totalCost += restaurantMenuService.getPriceByNameAndId(selectedRestaurant.getId(), item.getItemName());
                totalQuantity += item.getQuantity();
            }
            selectedRestaurant.incrementProcessingLoad(totalQuantity);
        }
        else{
            throw new RuntimeException("No available restaurant can fulfill this order.");
        }

        scheduleCapacityRelease(selectedRestaurant, totalQuantity, order, selectedRestaurant.getProcessingTimePerItem()*totalQuantity);
        order.setCustomer(user);
        order.setRestaurant(selectedRestaurant);
        order.setTotalAmount(totalCost);
        order.setOrderStatus(OrderStatus.PROCESSING);

        orderRepository.save(order);
        return order;
    }

    private boolean canFulfillOrder(Restaurant restaurant, List<OrderItemDTO> items){
        for (OrderItemDTO itemRequest : items) {
            RestaurantMenuItem menuItem = restaurantMenuService.getMenuItemByNameAndId(restaurant.getId(),itemRequest.getItemName());
            if (menuItem == null || menuItem.getQuantity() < itemRequest.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void scheduleCapacityRelease(Restaurant restaurant, int quantity, Order order, long delayInSeconds) {
        scheduler.schedule(() -> {
            restaurant.releaseCapacity(quantity);
            restaurantService.updateRestaurant(restaurant.getId(),restaurant); // Update restaurant capacity

            // Update the order status if all items have been processed
            checkAndUpdateOrderStatus(order);
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    private synchronized void checkAndUpdateOrderStatus(Order order) {
        order.setOrderStatus(OrderStatus.FULFILLED);
        orderRepository.save(order); // Save the updated order status
    }

//    private Order fulfillOrderFromEligibleRestaurants(List<Restaurant> restaurants, List<OrderItemDTO> items) {
//        Order order = new Order();
//        order.setOrderStatus(OrderStatus.PROCESSING);
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        for (Restaurant restaurant : restaurants) {
//            if (restaurant.hasProcessingCapacity()) {
//                for (OrderItemDTO itemRequest : items) {
//                    RestaurantMenuItem menuItem =restaurantMenuService.getMenuItemByNameAndId(restaurant.getId(),itemRequest.getItemName());
//
//                    // Check if the restaurant can fulfill the item
//                    if (menuItem != null && menuItem.getQuantity() >= itemRequest.getQuantity()) {
//                        OrderItem orderItem = new OrderItem();
//                        orderItem.setRestaurantMenuItem(menuItem);
//                        orderItem.setQuantity(itemRequest.getQuantity());
//                        orderItem.setTotalPrice(menuItem.getPrice());
//
//                        // Reduce the quantity in the menu item and add to the order
//                        menuItem.reduceQuantity(itemRequest.getQuantity());
//                        orderItems.add(orderItem);
//                    }
//                }
//                // If we have items added to order from this restaurant, finalize it
//                if (!orderItems.isEmpty()) {
//                    order.setRestaurant(restaurant);
//                    order.setOrderItems(orderItems);
//                    orderRepository.save(order);
//                    restaurant.reserveCapacity();
//                    return order;
//                }
//            }
//        }
//        return null; // If no restaurant could fulfill any part of the order
//    }

//    private OrderDTO convertToDTO(Order order) {
//        // Logic to convert Order to OrderDTO
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(order.getId());
//        orderDTO.setStatus(order.getStatus());
//
//        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
//        for (OrderItem item : order.getItems()) {
//            OrderItemDTO itemDTO = new OrderItemDTO();
//            itemDTO.setItemName(item.getRestaurantMenuItem().getItemName());
//            itemDTO.setQuantity(item.getQuantity());
//            itemDTO.setPrice(item.getPrice());
//            orderItemDTOs.add(itemDTO);
//        }
//        orderDTO.setItems(orderItemDTOs);
//        return orderDTO;
//    }

//    @Override
//    public Order createOrder(OrderDTO orderDTO, User user) throws Exception {
//
//
//        Restaurant restaurant = restaurantService.findById(orderDTO.getRestaurantId());
//
//        Order createdOrder = new Order();
//        createdOrder.setCustomer(user);
//        createdOrder.setOrderStatus(OrderStatus.PENDING);
//        createdOrder.setRestaurant(restaurant);
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (OrderItemDTO userOrderItem : orderDTO.getItems()) {
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setQuantity(userOrderItem.getQuantity());
//            orderItem.setTotalPrice(userOrderItem.getPrice());
//
//            OrderItem saveOrderItem = orderItemRepository.save(orderItem);
//            orderItems.add(saveOrderItem);
//        }
//
//        Long totalPrice = cartService.calculateTotal(cart);
//
//        createdOrder.setOrderItems(orderItems);
//        createdOrder.setTotalPrice(totalPrice);
//
//        Order savedOrder = orderRepository.save(createdOrder);
//
//        restaurant.getOrders().add(savedOrder);
//
//        return createdOrder;
//    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order = findOrderById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {

            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
            return orderRepository.save(order);
        }

        throw new BadRequestException("Please Select a Valid Order Status");
    }

    @Override
    public boolean canselOrder(Long orderId) throws Exception {

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
