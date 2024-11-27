package com.brytech.prescription_service.filestorage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobStorageException;
import com.brytech.prescription_service.exceptions.FileUploadFailedException;
import com.brytech.prescription_service.exceptions.RequestValidationException;
import com.brytech.prescription_service.exceptions.UnsupportedFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AzureFileStorageClient implements FileStorageClient {

    private static final Logger logger = LoggerFactory.getLogger(AzureFileStorageClient.class);

    private final List<String> ALLOWED_FILE_TYPES = List.of("image/png", "image/jpeg", "application/pdf");
    private final BlobServiceClient blobServiceClient;

    public AzureFileStorageClient(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }
 

    @Override
    public String uploadFile(String containerName, String originalFileName, InputStream data, long length,
            String mimeType) throws IOException {
        validateInputs(containerName, originalFileName, data, length);
        validateFileType(mimeType);

        try {
            // Ensure container exists or create it
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            if (!blobContainerClient.exists()) {
                blobContainerClient.create();
            }

            // Generate a unique file name
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

            // Get BlobClient object to interact with specified blob
            BlobClient blobClient = blobContainerClient.getBlobClient(uniqueFileName);

            // Upload the file
            try (data) {
                blobClient.upload(data, length, true);
            }

            logger.info("File uploaded successfully: {}", uniqueFileName);
            return blobClient.getBlobUrl();

        } catch (BlobStorageException e) {
            logger.error("Failed to upload file [{}] to Azure Blob Storage", originalFileName, e);
            throw new FileUploadFailedException("Failed to upload file to Azure Blob Storage");
        } catch (IOException e) {
            logger.error("Error closing InputStream after uploading file [{}]", originalFileName, e);
            throw new FileUploadFailedException("Error closing InputStream");
        }
    }

    private void validateInputs(String containerName, String originalFileName, InputStream data, long length) {
        if (containerName == null || containerName.isBlank()) {
            throw new RequestValidationException("Container name must not be null or empty");
        }

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new RequestValidationException("File name must not be null or empty");
        }

        if (data == null) {
            throw new RequestValidationException("File data must not be null");
        }

        if (length <= 0) {
            throw new RequestValidationException("File length must be greater than zero");
        }
    }

    private void validateFileType(String mimeType) {
        if (!ALLOWED_FILE_TYPES.contains(mimeType)) {
            throw new UnsupportedFileException("Unsupported file type: " + mimeType);
        }
    }  
}
