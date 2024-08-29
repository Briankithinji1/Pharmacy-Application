package com.brytech.messaging_service.model;

import com.brytech.messaging_service.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document(collection = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {

    @Id
    private String messageId;
    private String conversationId;
    private String senderId;
    private String receiverId;
    private String content;
    private Instant timestamp;
    private MessageStatus status;
    private boolean read;
    private boolean deleted;
}
