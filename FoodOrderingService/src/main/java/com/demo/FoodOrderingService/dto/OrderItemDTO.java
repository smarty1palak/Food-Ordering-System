package com.demo.FoodOrderingService.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String itemName;
    private int quantity;
    private double price;
}
