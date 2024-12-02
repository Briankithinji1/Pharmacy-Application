package com.brytech.prescription_service.controller;

import com.brytech.prescription_service.dto.PrescriptionUploadDTO;
import com.brytech.prescription_service.enums.PrescriptionStatus;
import com.brytech.prescription_service.exceptions.ApiError;
import com.brytech.prescription_service.service.PrescriptionUploadService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class PrescriptionUploadController {

    private final PrescriptionUploadService prescriptionUploadService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prescription uploaded successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionUploadDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate file for customer",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionUploadDTO> savePrescriptionUpload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") @Valid PrescriptionUploadDTO uploadDTO) throws IOException {

        PrescriptionUploadDTO savedUpload = prescriptionUploadService.save(
                uploadDTO,
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUpload);
    }

    @GetMapping("/customer/{customerId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "No files found for the customer",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionUploadDTO>> findFilesByCustomerId(
            @PathVariable("customerId") Long customerId) {
        List<PrescriptionUploadDTO> files = prescriptionUploadService.findFileByCustomerId(customerId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/prescription/{prescriptionId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "No files found for the linked prescription",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionUploadDTO>> findFilesByPrescriptionId(
            @PathVariable("prescriptionId") Long prescriptionId) {
        List<PrescriptionUploadDTO> files = prescriptionUploadService.findPrescriptionByLinkedPrescriptionId(prescriptionId);
        return ResponseEntity.ok(files);
    }

    @PatchMapping("/{id}/status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionUploadDTO.class))),
            @ApiResponse(responseCode = "404", description = "Uploaded prescription not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status update request",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionUploadDTO> updateUploadStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") PrescriptionStatus newStatus) {
        PrescriptionUploadDTO updatedUpload = prescriptionUploadService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedUpload);
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Uploaded prescription deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Uploaded prescription not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteUploadedPrescription(@PathVariable("id") Long id) {
        prescriptionUploadService.deletedUploadedPrescriptionById(id);
        return ResponseEntity.noContent().build();
    }
}
