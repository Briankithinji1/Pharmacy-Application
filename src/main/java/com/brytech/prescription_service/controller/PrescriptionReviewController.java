package com.brytech.prescription_service.controller;

import com.brytech.prescription_service.dto.PrescriptionReviewDTO;
import com.brytech.prescription_service.exceptions.ApiError;
import com.brytech.prescription_service.service.PrescriptionReviewService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class PrescriptionReviewController {

    private final PrescriptionReviewService prescriptionReviewService;

    @GetMapping("/prescription/{prescriptionId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "No reviews found for the prescription",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionReviewDTO>> getReviewsByPrescriptionId(
            @PathVariable("prescriptionId") Long prescriptionId) {
        List<PrescriptionReviewDTO> reviews = prescriptionReviewService.findReviewsByPrescriptionId(prescriptionId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviewer/{pharmacistId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "No reviews found for the reviewer",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionReviewDTO>> getReviewsByReviewerId(
            @PathVariable("pharmacistId") Long pharmacistId) {
        List<PrescriptionReviewDTO> reviews = prescriptionReviewService.findReviewsByReviewerId(pharmacistId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDTO> getReviewById(
            @PathVariable("reviewId") Long reviewId) {
        PrescriptionReviewDTO review = prescriptionReviewService.findReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/{pharmacistId}/{prescriptionUploadId}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review created successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Pharmacist or prescription not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDTO> createReview(
            @PathVariable("pharmacistId") Long pharmacistId,
            @PathVariable("prescriptionUploadId") Long prescriptionUploadId,
            @RequestBody @Valid PrescriptionReviewDTO reviewDTO) {
        PrescriptionReviewDTO createdReview = prescriptionReviewService.createReview(
                pharmacistId,
                prescriptionUploadId,
                reviewDTO
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PatchMapping("/{reviewId}/reviewer/{pharmacistId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review updated successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or unauthorized update",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDTO> updateReview(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("pharmacistId") Long pharmacistId,
            @RequestBody @Valid PrescriptionReviewDTO reviewDTO) {
        PrescriptionReviewDTO updatedReview = prescriptionReviewService.updateReview(
                reviewId,
                pharmacistId,
                reviewDTO
        );
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
        prescriptionReviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }
}
