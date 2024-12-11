package com.brytech.order_service.enumeration;

public enum OrderStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    PENDING_PAYMENT("PENDING_PAYMENT"),
    PAID("PAID"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    DELIVERED("DELIVERED"),
    REFUND("REFUND"),
    CANCELLED("CANCELLED"),
    RETURNED("Returned"),

    REJECT("REJECT");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
