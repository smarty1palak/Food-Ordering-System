package com.mahel.FoodOrderingService.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long restaurantId;
    private Address deliveryAddress;

}
