package com.brytech.messaging_service.model;

import com.brytech.messaging_service.enums.UserRole;
import com.brytech.messaging_service.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String username;
    private UserRole role; // "pharmacist" or "patient" or "courier"
    private UserStatus onlineStatus; // online, offline
}
