package com.brytech.messaging_service.service;

import com.brytech.messaging_service.dto.ConversationDto;
import com.brytech.messaging_service.model.Conversation;
import com.brytech.messaging_service.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ModelMapper modelMapper;

    public Optional<String> getConversationId(
            String senderId,
            String recipientId,
            boolean createNewConversationIfNotExists
    ) {
        return conversationRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(Conversation::getChatId)
                .or(() -> {
                    if (createNewConversationIfNotExists) {
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }

                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        Conversation senderRecipient = Conversation
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        Conversation recipientSender = Conversation
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        conversationRepository.save(senderRecipient);
        conversationRepository.save(recipientSender);

        return chatId;
    }

    public List<ConversationDto> listUserConversations(String id) {
        return conversationRepository.findAllById(id)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private ConversationDto convertToDto(Conversation conversation) {
        return modelMapper.map(conversation, ConversationDto.class);
    }
}
