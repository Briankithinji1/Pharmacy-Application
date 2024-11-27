package com.brytech.prescription_service.service;

import com.brytech.prescription_service.dao.PrescriptionDao;
import com.brytech.prescription_service.dao.PrescriptionReviewDao;
import com.brytech.prescription_service.dao.PrescriptionUploadDao;
import com.brytech.prescription_service.dto.PrescriptionDto;
import com.brytech.prescription_service.dto.PrescriptionReviewDTO;
import com.brytech.prescription_service.dto.PrescriptionUploadDTO;
import com.brytech.prescription_service.enums.PrescriptionStatus;
import com.brytech.prescription_service.events.PrescriptionCreatedEvent;
import com.brytech.prescription_service.events.PrescriptionEventPublisher;
import com.brytech.prescription_service.events.PrescriptionReviewedEvent;
import com.brytech.prescription_service.events.PrescriptionUpdatedEvent;
import com.brytech.prescription_service.events.PrescriptionUploadedEvent;
import com.brytech.prescription_service.exceptions.ResourceNotFoundException;
import com.brytech.prescription_service.models.Prescription;
import com.brytech.prescription_service.models.PrescriptionReview;
import com.brytech.prescription_service.models.PrescriptionUpload;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionDao prescriptionDao;
    private final PrescriptionUploadDao uploadDao;
    private final PrescriptionReviewDao reviewDao;
//    private final OutboxRepository outboxRepository;
    private final ModelMapper mapper;
    private final PrescriptionEventPublisher eventPublisher;


    public PrescriptionDto createPrescription(Long prescriptionId, PrescriptionDto prescriptionDto) {
        Prescription prescription = mapper.map(prescriptionDto, Prescription.class);

        Prescription savedPrescription = prescriptionDao.save(prescription);
        PrescriptionCreatedEvent event = new PrescriptionCreatedEvent(
                savedPrescription.getId(), 
                savedPrescription.getCustomer().getId(), 
                savedPrescription.getPrescriptionNumber(), 
                savedPrescription.getStatus(), 
                savedPrescription.getCreatedAt()
        );

        eventPublisher.publishPrescriptionCreatedEvent(prescriptionId, event);
    
        return convertToDto(savedPrescription);
    }


    // TODO: Remove this method and publish event in the save method(PrescriptionUploadService)
    //
    public PrescriptionUploadDTO uploadPrescription(Long prescriptionUploadId, PrescriptionUploadDTO uploadDTO) {
        PrescriptionUpload prescriptionUpload = mapper.map(uploadDTO, PrescriptionUpload.class);

        PrescriptionUpload savedUpload = uploadDao.save(prescriptionUpload);
        PrescriptionUploadedEvent event = new PrescriptionUploadedEvent(
                savedUpload.getId(), 
                savedUpload.getCustomer().getId(), 
                savedUpload.getFileName(), 
                savedUpload.getFileType(), 
                savedUpload.getFileUrl(), 
                savedUpload.getStatus(), 
                savedUpload.getUploadDate()
        );

        eventPublisher.publishPrescriptionUploadedEvent(prescriptionUploadId, event);

        return convertToUploadDto(savedUpload);
    }

    
    public PrescriptionReviewDTO reviewPrescription(Long reviewId, PrescriptionReviewDTO reviewDTO) {
        PrescriptionReview prescriptionReview = mapper.map(reviewDTO, PrescriptionReview.class);

        PrescriptionReview savedReview = reviewDao.save(prescriptionReview);
        PrescriptionReviewedEvent event = new PrescriptionReviewedEvent(
                savedReview.getId(), 
                savedReview.getPrescriptionUpload().getId(), 
                savedReview.getReviewer().getId(), 
                savedReview.getReviewStatus(), 
                savedReview.getComments(), 
                savedReview.getReviewedAt()
        );

        eventPublisher.publishPrescriptionReviewedEvent(reviewId, event);

        return convertToReviewDto(savedReview);
    }


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

    private PrescriptionUploadDTO convertToUploadDto(PrescriptionUpload upload) {
        return mapper.map(upload, PrescriptionUploadDTO.class);
    }

    private PrescriptionReviewDTO convertToReviewDto(PrescriptionReview review) {
        return mapper.map(review, PrescriptionReviewDTO.class);
    }

}
