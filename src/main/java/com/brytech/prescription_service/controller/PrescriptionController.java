package com.brytech.prescription_service.controller;

import com.brytech.prescription_service.dto.PrescriptionDto;
import com.brytech.prescription_service.exceptions.ApiError;
import com.brytech.prescription_service.service.PrescriptionService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("/{prescriptionId}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prescription created successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate prescription",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionDto> createPrescription(
            @PathVariable("prescriptionId") Long prescriptionId,
            @RequestBody @Valid PrescriptionDto prescriptionDto
    ) {
        PrescriptionDto createdPrescription = prescriptionService.createPrescription(prescriptionId, prescriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrescription);
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<PrescriptionDto>> getAllPrescriptions(Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.getAllPrescriptions(pageable);
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescription retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Prescription not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionDto> findPrescriptionById(@PathVariable("id") Long id) {
        PrescriptionDto prescription = prescriptionService.findPrescriptionById(id);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/status/{status}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully by status",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the given status",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<PrescriptionDto>> findPrescriptionByStatus(
            @PathVariable("status") String status,
            Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.findPrescriptionByStatus(status, pageable);
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/patient/{customerId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully by patient ID",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "No prescriptions found for the given patient ID",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<PrescriptionDto>> findPrescriptionByPatientId(
            @PathVariable("customerId") Long customerId,
            Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.findPrescriptionByPatientId(customerId, pageable);
        return ResponseEntity.ok(prescriptions);
    }

    @PutMapping("/{prescriptionId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescription updated successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Prescription not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionDto> updatePrescription(
            @PathVariable("prescriptionId") Long prescriptionId,
            @RequestBody @Valid PrescriptionDto prescriptionDto) {
        PrescriptionDto updatedPrescription = prescriptionService.updatePrescription(prescriptionId, prescriptionDto);
        return ResponseEntity.ok(updatedPrescription);
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prescription deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Prescription not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deletePrescription(@PathVariable("id") Long id) {
        prescriptionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
