package com.brytech.order_service.enumeration;

public enum OrderStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    PENDING_PAYMENT("Pending Payment"),
    PAID("PAID"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    DELIVERED("DELIVERED"),
    REFUND("REFUND"),
    CANCELLED("CANCELLED"),
    RETURNED("Returned"),
    PRESCRIPTION_VALIDATED("Prescription Validated"),
    DELIVERY_SCHEDULED("Delivery Scheduled"),
    AWAITING_PRESCRIPTION_VALIDATION("Awaiting Prescription Validation"),

    REJECT("REJECT");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
