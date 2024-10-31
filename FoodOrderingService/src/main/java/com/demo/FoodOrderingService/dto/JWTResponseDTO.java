package com.demo.FoodOrderingService.dto;

import com.demo.FoodOrderingService.enums.UserRole;
import lombok.Data;

@Data
public class JWTResponseDTO {

    private String jwt;

    private String email;

    private String userName;

    private UserRole role;
}
