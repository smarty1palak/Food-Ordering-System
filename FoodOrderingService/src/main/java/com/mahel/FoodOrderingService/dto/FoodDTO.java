package com.mahel.FoodOrderingService.dto;

import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {

    private String name;
    private String description;
    private Long price;
    private Long restaurantId;
}
