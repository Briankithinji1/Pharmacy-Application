package com.brytech.order_service.event.incoming;

import java.time.Instant;

public record PrescriptionValidatedEvent(
        Long orderId,
        Long prescriptionId,
        Instant validatedAt
) {}


/* TODO: CHANGE TO PRESCRIPTIONVERIFIEDEVENT 
 * - Received from the PHARMACISTSERVICE
 * - Should include prescriptionId, pharmacistId, verificationTimestamp, 
 *   medicationList (extracted from prescription or selected by pharmacist), 
 *   verificationStatus = "VERIFIED"
 */
