package com.brytech.messaging_service.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Document(collection = "conversations")
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

    @Id
    private String id;
    private List<String> participantIds;
    private Instant lastUpdated;
}
