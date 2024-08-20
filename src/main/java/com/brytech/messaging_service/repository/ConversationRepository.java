package com.brytech.messaging_service.repository;

import com.brytech.messaging_service.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation, String> {


}
