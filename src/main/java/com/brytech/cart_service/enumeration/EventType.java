package com.brytech.cart_service.enumeration;

public enum EventType {
    CART_CREATED,
    ITEM_ADDED_TO_CART,
    ITEM_REMOVED_FROM_CART,
    CART_ITEM_QUANTITY_UPDATED,
    CART_CLEARED,
    CART_CHECKED_OUT,
    CART_ABANDONED,

    PRODUCT_PRICE_UPDATED,
    PRODUCT_DELETED
}
