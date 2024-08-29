package com.brytech.messaging_service.repository;

import com.brytech.messaging_service.enums.MessageStatus;
import com.brytech.messaging_service.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByMessageId(String messageId);

    List<Message> findByConversationIdOrderByTimestampAsc(String conversationId);

    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

    List<Message> findBySenderId(String senderId);

    List<Message> findByConversationIdAndStatus(String conversationId, MessageStatus status);
}
