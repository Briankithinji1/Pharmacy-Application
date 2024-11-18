package com.brytech.prescription_service.service;

import com.brytech.prescription_service.dao.PrescriptionDao;
import com.brytech.prescription_service.dto.PrescriptionDto;
import com.brytech.prescription_service.models.Prescription;
import com.brytech.prescription_service.repository.OutboxRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionDao prescriptionDao;
    private final OutboxRepository outboxRepository;
    private final ModelMapper mapper;


    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
        Prescription prescription = mapper.map(prescriptionDto, Prescription.class);

        Prescription savedPrescription = prescriptionDao.save(prescription);

        return convertToDto(savedPrescription);
    }



    private PrescriptionDto convertToDto(Prescription prescription) {
        return mapper.map(prescription, PrescriptionDto.class);
    }
}
