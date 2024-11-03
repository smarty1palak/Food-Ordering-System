package com.demo.FoodOrderingService.enums;

public enum OrderStatus {
    PENDING,          // Order has been created but not yet processed
    PROCESSING,       // Order is being processed by restaurants
    FULFILLED,        // Order is fully fulfilled and ready for dispatch
}