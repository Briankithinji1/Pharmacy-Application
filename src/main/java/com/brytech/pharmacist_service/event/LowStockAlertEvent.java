package com.brytech.pharmacist_service.event;

public record LowStockAlertEvent(
        Long productId,
        String productName,
        int remainingStock,
        int lowStockThreshold
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }
}
