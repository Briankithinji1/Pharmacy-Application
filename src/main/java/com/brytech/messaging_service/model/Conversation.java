package com.brytech.messaging_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@Document(collection = "conversations")
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private Instant lastUpdated;
}
