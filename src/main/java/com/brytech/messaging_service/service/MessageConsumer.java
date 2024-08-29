package com.brytech.messaging_service.service;

import com.brytech.messaging_service.model.Message;
import com.brytech.messaging_service.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumer {

    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "chat-messages", groupId = "chat-room")
    public void listen(Message message) {
        messageRepository.save(message);

        messagingTemplate.convertAndSend("/topic/public", message);
    }
}
