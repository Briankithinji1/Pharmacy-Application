package com.brytech.prescription_service.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import com.brytech.prescription_service.dao.PrescriptionUploadDao;
import com.brytech.prescription_service.dto.PrescriptionUploadDTO;
import com.brytech.prescription_service.enums.PrescriptionStatus;
import com.brytech.prescription_service.events.PrescriptionEventPublisher;
import com.brytech.prescription_service.events.PrescriptionUploadedEvent;
import com.brytech.prescription_service.exceptions.DuplicateResourceException;
import com.brytech.prescription_service.exceptions.FileUploadFailedException;
import com.brytech.prescription_service.exceptions.RequestValidationException;
import com.brytech.prescription_service.exceptions.ResourceNotFoundException;
import com.brytech.prescription_service.filestorage.FileStorageClient;
import com.brytech.prescription_service.models.PrescriptionUpload;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionUploadService {

    private final PrescriptionUploadDao uploadDao;
    private final ModelMapper mapper;
    private final FileStorageClient fileStorageClient;
    private final PrescriptionEventPublisher eventPublisher;

    public PrescriptionUploadDTO save(PrescriptionUploadDTO upload, InputStream fileData, long fileSize, String mimeType) throws IOException {

        if (uploadDao.existsByFileNameAndCustomerId(upload.fileName(), upload.customerId())) {
            throw new DuplicateResourceException("File with the same name already exists for this customer.");
        }

        if (fileData == null) {
            throw new RequestValidationException("Upload data and file stream must not be null");
        }

        // Upload the file to Azure Blob Storage
        String fileUrl;
        try {
            fileUrl = fileStorageClient.uploadFile(
                    "prescriptions", 
                    upload.fileName(), 
                    fileData, 
                    fileSize, 
                    mimeType
            );
        } catch (FileUploadFailedException e) {
            throw new FileUploadFailedException("Failed to upload prescription to Azure storage");
        }

        upload = upload.toBuilder()
                        .fileUrl(fileUrl)
                        .uploadDate(Instant.now())
                        .build();
        
        PrescriptionUpload prescriptionUpload = convertToEntity(upload);
        PrescriptionUpload savedUpload = uploadDao.save(prescriptionUpload);

        // Publish event
        PrescriptionUploadedEvent event = new PrescriptionUploadedEvent(
                savedUpload.getId(),
                savedUpload.getCustomer().getId(),
                savedUpload.getFileName(),
                savedUpload.getFileType(),
                savedUpload.getFileUrl(),
                savedUpload.getStatus(),
                savedUpload.getUploadDate()
        );
        eventPublisher.publishPrescriptionUploadedEvent(savedUpload.getId(), event);

        return convertToDTO(savedUpload);
    }

    public List<PrescriptionUploadDTO> findFileByCustomerId(Long customerId) {
        return uploadDao.findByCustomerId(customerId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public List<PrescriptionUploadDTO> findPrescriptionByLinkedPrescriptionId(Long prescriptionId) {
        return uploadDao.findByLinkedPrescriptionId(prescriptionId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    @Transactional
    public PrescriptionUploadDTO updateStatus(Long id, PrescriptionStatus newStatus) {
        PrescriptionUpload entity = uploadDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription upload not found for ID: " + id));

        entity.setStatus(newStatus);

        PrescriptionUpload savedEntity = uploadDao.save(entity);

        return convertToDTO(savedEntity);
    }

    public void deletedUploadedPrescriptionById(Long id) {
        try {
            uploadDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Uploaded prescription with ID [%s] does not exist".formatted(id)
            );
        }
    }

    private PrescriptionUploadDTO convertToDTO(PrescriptionUpload upload) {
        return mapper.map(upload, PrescriptionUploadDTO.class);
    }

    private PrescriptionUpload convertToEntity(PrescriptionUploadDTO uploadDTO) {
        return mapper.map(uploadDTO, PrescriptionUpload.class);
    }
}
