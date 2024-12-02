package com.brytech.prescription_service.service;

import com.brytech.prescription_service.dao.PrescriptionDao;
import com.brytech.prescription_service.dto.PrescriptionDto;
import com.brytech.prescription_service.enums.PrescriptionStatus;
import com.brytech.prescription_service.events.PrescriptionCreatedEvent;
import com.brytech.prescription_service.events.PrescriptionEventPublisher;
import com.brytech.prescription_service.events.PrescriptionUpdatedEvent;
import com.brytech.prescription_service.exceptions.DuplicateResourceException;
import com.brytech.prescription_service.exceptions.ResourceNotFoundException;
import com.brytech.prescription_service.models.Prescription;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionDao prescriptionDao;
    private final ModelMapper mapper;
    private final PrescriptionEventPublisher eventPublisher;

    @Transactional
    public PrescriptionDto createPrescription(Long prescriptionId, PrescriptionDto prescriptionDto) {

        if (prescriptionDao.isPrescriptionExistById(prescriptionId)) {
            throw new DuplicateResourceException("Prescription with ID [%s] already exists.".formatted(prescriptionId));
        }

        Prescription prescription = mapper.map(prescriptionDto, Prescription.class);
        Prescription savedPrescription = prescriptionDao.save(prescription);

        // Publish event
        PrescriptionCreatedEvent event =  buildPrescriptionCreatedEvent(savedPrescription);
        eventPublisher.publishPrescriptionCreatedEvent(prescriptionId, event);
    
        return convertToDto(savedPrescription);
    }

    public Page<PrescriptionDto> getAllPrescriptions(Pageable pageable) {
        return prescriptionDao.selectAllPrescriptions(pageable)
                .map(this::convertToDto);
    }

    public PrescriptionDto findPrescriptionById(Long id) {
        return prescriptionDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription with ID [%s] not found".formatted(id)
            ));
    }

    public Page<PrescriptionDto> findPrescriptionByStatus(String status, Pageable pageable) {
        return prescriptionDao.findByStatus(status, pageable)
            .map(this::convertToDto);
    }

    public Page<PrescriptionDto> findPrescriptionByPatientId(Long customerId, Pageable pageable) {
        if (!prescriptionDao.isPrescriptionExistByCustomerId(customerId)) {
           throw new ResourceNotFoundException(
                   "Prescription with ID [%s] not found".formatted(customerId)
           );
        }

        return  prescriptionDao.findByCustomerId(customerId, pageable)
                .map(this::convertToDto);
    }

    public void deleteById(Long id) {
        if (!prescriptionDao.isPrescriptionExistById(id)) {
            throw new ResourceNotFoundException(
                    "Prescription with ID [%s] not found".formatted(id)
            );
        }

        prescriptionDao.deleteById(id);
    }

    @Transactional
    public PrescriptionDto updatePrescription(Long prescriptionId, PrescriptionDto prescriptionDto) {
        // Current prescription (old state)
        Prescription existingPrescription = prescriptionDao.findById(prescriptionId)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with ID: " + prescriptionId));

        PrescriptionStatus oldStatus = existingPrescription.getStatus();

        Prescription updatedPrescription = mapper.map(prescriptionDto, Prescription.class);
        updatedPrescription.setId(prescriptionId);

        // Save updated entity
        Prescription savedPrescription = prescriptionDao.save(updatedPrescription);

        if (!oldStatus.equals(savedPrescription.getStatus())) {
            PrescriptionUpdatedEvent event = new PrescriptionUpdatedEvent(
                savedPrescription.getId(),
                oldStatus, // old status 
                savedPrescription.getStatus(), // new status
                savedPrescription.getUpdatedAt()
            );

            eventPublisher.publishPrescriptionUpdatedEvent(prescriptionId, event);
        }
         
        return convertToDto(savedPrescription);
    }

    private PrescriptionDto convertToDto(Prescription prescription) {
        return mapper.map(prescription, PrescriptionDto.class);
    }

    private PrescriptionCreatedEvent buildPrescriptionCreatedEvent(Prescription prescription) {
        return new PrescriptionCreatedEvent(
                prescription.getId(),
                prescription.getCustomer().getId(),
                prescription.getPrescriptionNumber(),
                prescription.getStatus(),
                prescription.getCreatedAt()
        );
    }
}
