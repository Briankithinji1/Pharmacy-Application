package com.example.Pharmacy.Application.payment.enums;

import java.util.Collections;
import java.util.List;

public enum PaymentType {
    MPESA(Collections.singletonList("mpesa")),
    CASH(Collections.singletonList("cash"));

    private final List<String> stringList;

    PaymentType(List<String> stringList) {
        this.stringList = stringList;
    }

    public static PaymentType getPaymentTypeFromString(String string) {
        for (PaymentType type: PaymentType.values()) {
            if (type.stringList.stream().anyMatch(str ->
                    str.equalsIgnoreCase(string))) {
                return type;
            }
        }
        return CASH;
    }
}
