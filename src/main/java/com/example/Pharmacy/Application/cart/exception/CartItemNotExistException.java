package com.example.Pharmacy.Application.cart.exception;

public class CartItemNotExistException extends IllegalArgumentException {
    public CartItemNotExistException(String message) {
        super(message);
    }
}
