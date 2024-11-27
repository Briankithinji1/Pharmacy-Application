package com.brytech.prescription_service.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import com.brytech.prescription_service.dao.PrescriptionUploadDao;
import com.brytech.prescription_service.dto.PrescriptionUploadDTO;
import com.brytech.prescription_service.enums.PrescriptionStatus;
import com.brytech.prescription_service.exceptions.FileUploadFailedException;
import com.brytech.prescription_service.exceptions.RequestValidationException;
import com.brytech.prescription_service.exceptions.ResourceNotFoundException;
import com.brytech.prescription_service.filestorage.FileStorageClient;
import com.brytech.prescription_service.models.PrescriptionUpload;

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
    
    // save or create upload
    public PrescriptionUploadDTO save(PrescriptionUploadDTO upload, InputStream fileData, long fileSize, String mimeType) throws IOException {
        if (upload == null || fileData == null) {
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

        return convertToDTO(savedUpload);
    }

    // findUploadFileByCustomerId
    public List<PrescriptionUploadDTO> findFileByCustomerId(Long customerId) {
        return uploadDao.findByCustomerId(customerId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    // findPrescriptionByLinkedPrescriptionId
    public List<PrescriptionUploadDTO> findPrescriptionByLinkedPrescriptionId(Long prescriptionId) {
        return uploadDao.findByLinkedPrescriptionId(prescriptionId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    // updateStatus
    public PrescriptionUploadDTO updateStatus(Long id, PrescriptionStatus newStatus) {
        PrescriptionUpload entity = uploadDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescription upload not found for ID: " + id));

        PrescriptionUploadDTO dto = convertToDTO(entity);

        // Update status in the DTO
        dto = dto.toBuilder()
                    .status(newStatus)
                    .build();

        PrescriptionUpload updatedEntity = convertToEntity(dto);

        PrescriptionUpload savedEntity = uploadDao.save(updatedEntity);

        return convertToDTO(savedEntity);
    }

    // deleteUploadedFileById
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
