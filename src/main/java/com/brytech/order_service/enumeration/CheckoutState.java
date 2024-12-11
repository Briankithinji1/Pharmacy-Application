package com.brytech.order_service.enumeration;

public enum CheckoutState {
    CART("Cart"),
    PAYMENT_PENDING("Payment Pending"),
    PAYMENT_SUCCESSFUL("Payment Successful"),
    PAYMENT_FAILED("Payment Failed"),
    ORDER_PLACED("Order Placed"),
    ORDER_SHIPPED("Order Shipped"),
    ORDER_DELIVERED("Order Delivered"),
    ORDER_CANCELED("Order Cancelled");

    private final String name;

    CheckoutState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
