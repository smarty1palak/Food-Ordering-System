package com.demo.FoodOrderingService.service;

import com.demo.FoodOrderingService.dto.UserDTO;
import com.demo.FoodOrderingService.model.User;

public interface UserService {

    public User registerUser(UserDTO userDTO) throws Exception;

    public User userByToken(String token) throws Exception;

    public User userByEmail(String email) throws Exception;
}
