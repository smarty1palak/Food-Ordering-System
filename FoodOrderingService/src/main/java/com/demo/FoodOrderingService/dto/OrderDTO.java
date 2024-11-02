package com.demo.FoodOrderingService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private List<OrderItemDTO> items;

}
