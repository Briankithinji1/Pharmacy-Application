package main.java.com.example.Pharmacy.Application.payment.enums;

import java.util.Collections;
import java.util.List;

public enum TransactionStatus {
    PENDING(Collections.singletonList("pending")),
    COMPLETED(Collections.singletonList("completed")),
    REFUNDED(Collections.singletonList("refunded"));

    private final List<String> stringList;

    TransactionStatus(List<String> stringList) {
        this.stringList = stringList;
    }

    public static TransactionStatus getTransactionStatusFromString(String string) {
        for (TransactionStatus status: TransactionStatus.values()) {
            if (status.stringList.stream().anyMatch(str ->
                    str.equalsIgnoreCase(string))) {
                return status;
            }
        }
        return PENDING;
    }
}
