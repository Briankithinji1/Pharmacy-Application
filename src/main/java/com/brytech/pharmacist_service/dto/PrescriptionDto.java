package com.brytech.pharmacist_service.dto;

import com.brytech.pharmacist_service.enumeration.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrescriptionDto {

    private Long prescriptionId;
    private Long customerId;
    private LocalDateTime uploadDate;
    private PrescriptionStatus status;

    private String notes;
    private Long pharmacistId;
    private LocalDateTime reviewDate;
    private List<PrescriptionItemDto> items;
}
