package com.demo.FoodOrderingService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String name;

    private String ownerFullName;

    private String ownerEmail;

    private Double rating;

    private Integer processingCapacity;

    private Integer currLoad;

    private long processingTimePerItem;

    private String description;

}
