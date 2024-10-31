package com.demo.FoodOrderingService.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private String status;
    private List<OrderItemDTO> items;

}
