package com.brytech.messaging_service.model;

import com.brytech.messaging_service.enums.UserRole;
import com.brytech.messaging_service.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private UserRole role; // "pharmacist" or "patient" or "courier"
    private UserStatus onlineStatus; // online, offline
}
