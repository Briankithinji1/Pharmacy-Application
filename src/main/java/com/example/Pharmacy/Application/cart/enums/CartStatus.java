package com.example.Pharmacy.Application.cart.enums;

import java.util.Collections;
import java.util.List;

public enum CartStatus {
    OPEN(Collections.singletonList("Open")),
    CHECKED_OUT(Collections.singletonList("Checked Out")),
    CANCELED(Collections.singletonList("Canceled"));

    private final List<String> stringList;

    CartStatus(List<String> stringList) {
        this.stringList = stringList;
    }

    public static CartStatus getCartStatusFromString(String string) {
        for (CartStatus status: CartStatus.values()) {
            if (status.stringList.stream().anyMatch(str -> str.equalsIgnoreCase(string))) {
                return status;
            }
        }
        return OPEN;
    }
}
