package com.brytech.customer_service.model;

import com.brytech.customer_service.enums.PreferenceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID preference_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PreferenceType preference_type; // E.g "NotificationFrequency", "DeliveryPreferences"
    private String value; // E.g "Weekly", "HomeDelivery"
}
