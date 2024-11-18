package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.Outbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxDAO {

    void createOutboxEntry(Outbox outbox);
    Optional<Outbox> getOutboxEntryById(UUID messageId);
    List<Outbox> getPendingOutboxEntries();
    void markOutboxEntryAsSent(UUID messageId);
}
