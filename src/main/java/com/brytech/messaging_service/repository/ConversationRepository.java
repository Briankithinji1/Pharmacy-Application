package com.brytech.messaging_service.repository;

import com.brytech.messaging_service.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends MongoRepository<Conversation, String> {

    Optional<Conversation> findBySenderIdAndRecipientId(String senderId, String recipientId);

    List<Conversation> findAllById(String userId);
}
