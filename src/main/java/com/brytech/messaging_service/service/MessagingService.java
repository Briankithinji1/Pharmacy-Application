package com.brytech.messaging_service.service;

import com.brytech.messaging_service.dto.ConversationDto;
import com.brytech.messaging_service.dto.MessageDto;
import com.brytech.messaging_service.exceptions.MessageNotFoundException;
import com.brytech.messaging_service.model.Message;
import com.brytech.messaging_service.repository.ConversationRepository;
import com.brytech.messaging_service.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final ConversationRepository conversationRepository;

    public List<MessageDto> getConversationHistory(String senderId, String ReceiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, ReceiverId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public void markMessagesAsRead(String senderId, String receiverId) {
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        messages.forEach(message -> message.setRead(true));
        messageRepository.saveAll(messages);
    }

    public void deleteMessage(String messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("MessageNotFound"));
        message.setDeleted(true);
        messageRepository.save(message);
    }

    private Message convertToEntity(MessageDto messageDto) {
        return modelMapper.map(messageDto, Message.class);
    }

    private MessageDto convertToDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }
}
