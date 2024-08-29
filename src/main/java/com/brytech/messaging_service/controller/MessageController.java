package com.brytech.messaging_service.controller;

import com.brytech.messaging_service.dto.ConversationDto;
import com.brytech.messaging_service.dto.MessageDto;
import com.brytech.messaging_service.model.Message;
import com.brytech.messaging_service.service.ConversationService;
import com.brytech.messaging_service.service.MessageProducer;
import com.brytech.messaging_service.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageProducer messageProducer;
    private final MessagingService messagingService;
    private final ConversationService conversationService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message message) {
        messageProducer.sendMessage("chat-messages", message);
    }

    @GetMapping("/chat/history/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDto>> getChatHistory(
            @PathVariable String senderId,
            @PathVariable String receiverId
    ) {
        List<MessageDto> history = messagingService.getConversationHistory(senderId, receiverId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("chat/mark-as-read")
    public ResponseEntity<Void> markAsRead(
            @RequestParam String senderId,
            @RequestParam String receiverId
    ) {
        messagingService.markMessagesAsRead(senderId, receiverId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("chat/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable String messageId
    ) {
        messagingService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }


    // Conversation
    @GetMapping("/chat/conversations/{userId}")
    public ResponseEntity<List<ConversationDto>> listUserConversations(
            @PathVariable String userId
    ) {
        List<ConversationDto> conversations = conversationService.listUserConversations(userId);
        return ResponseEntity.ok(conversations);
    }
}
