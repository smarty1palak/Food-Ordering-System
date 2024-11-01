package com.demo.FoodOrderingService.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantMenuItemDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String itemName;

    private Double price;

    private Integer quantity;

    private String restaurantName;

    private String ownerEmail;
}
