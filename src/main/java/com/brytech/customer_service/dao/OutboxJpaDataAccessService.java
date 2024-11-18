package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.Outbox;
import com.brytech.customer_service.repository.OutboxRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("outboxJpa")
public class OutboxJpaDataAccessService implements OutboxDAO{

    private final OutboxRepository outboxRepository;

    public OutboxJpaDataAccessService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    @Override
    public void createOutboxEntry(Outbox outbox) {
        outboxRepository.save(outbox);
    }

    @Override
    public Optional<Outbox> getOutboxEntryById(UUID messageId) {
        return outboxRepository.findById(messageId);
    }

    @Override
    public List<Outbox> getPendingOutboxEntries() {
        return List.of();
    }

    @Override
    public void markOutboxEntryAsSent(UUID messageId) {

    }
}
