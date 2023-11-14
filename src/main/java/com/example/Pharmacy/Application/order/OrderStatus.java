package com.example.Pharmacy.Application.order;

import java.util.Collections;
import java.util.List;

public enum OrderStatus {
    PENDING(Collections.singletonList("Pending")),
    PROCESSING(Collections.singletonList("Processing")),
    SHIPPED(Collections.singletonList("Shipped")),
    DELIVERED(Collections.singletonList("Delivered")),
    CANCELED(Collections.singletonList("Canceled"));

    private final List<String> strings;

    OrderStatus(List<String> strings) {
        this.strings = strings;
    }

    public static OrderStatus getOrderStatusFromString(String string) {
        for (OrderStatus status: OrderStatus.values()) {
            if (status.strings.stream().anyMatch(str -> str.equalsIgnoreCase(string))) {
                return status;
            }
        }
        return PENDING;
    }
}
