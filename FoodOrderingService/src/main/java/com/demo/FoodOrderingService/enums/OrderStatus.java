package com.demo.FoodOrderingService.enums;

public enum OrderStatus {
    PENDING,          // Order has been created but not yet processed
    PROCESSING,       // Order is being processed by restaurants
    PARTIALLY_FULFILLED, // Order is partially fulfilled but waiting for more items
    FULFILLED,        // Order is fully fulfilled and ready for dispatch
    DISPATCHED,       // Order has been dispatched to the customer
    CANCELLED         // Order has been cancelled
}