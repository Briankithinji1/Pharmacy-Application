package com.brytech.pharmacist_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.brytech.pharmacist_service.dto.PrescriptionReviewDto;
import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import com.brytech.pharmacist_service.exception.ApiError;
import com.brytech.pharmacist_service.service.PrescriptionReviewService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/reviews")
public class PrescriptionReviewController {

    private final PrescriptionReviewService reviewService;

    public PrescriptionReviewController(PrescriptionReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(summary = "Create review", description = "Creates a new prescription review")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review created successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate resource",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDto> createReview(@RequestBody @Valid PrescriptionReviewDto reviewDto) {
        PrescriptionReviewDto createdReview = reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update review", description = "Updates an existing prescription review")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review updated successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Invalid operation",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDto> updateReview(
            @PathVariable Long reviewId,
            @RequestBody @Valid PrescriptionReviewDto reviewDto) {
        PrescriptionReviewDto updatedReview = reviewService.updateReview(reviewId, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Retrieves a prescription review by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDto> findReviewById(@PathVariable Long id) {
        PrescriptionReviewDto review = reviewService.findReviewByid(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Retrieves all prescription reviews with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PrescriptionReviewDto.class))))
    })
    public ResponseEntity<Page<PrescriptionReviewDto>> findAllReviews(Pageable pageable) {
        Page<PrescriptionReviewDto> reviews = reviewService.findAllReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get reviews by status", description = "Retrieves reviews by their status with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PrescriptionReviewDto.class))))
    })
    public ResponseEntity<Page<PrescriptionReviewDto>> findReviewByStatus(@PathVariable ReviewStatus status, Pageable pageable) {
        Page<PrescriptionReviewDto> reviews = reviewService.findReviewByStatus(status, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/pharmacist/{pharmacistId}")
    @Operation(summary = "Get reviews by pharmacist ID", description = "Retrieves reviews associated with a specific pharmacist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PrescriptionReviewDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionReviewDto>> findByPharmacistId(@PathVariable Long pharmacistId) {
        List<PrescriptionReviewDto> reviews = reviewService.findByPharmacistId(pharmacistId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/prescription/{prescriptionId}")
    @Operation(summary = "Get review by prescription ID", description = "Retrieves a review associated with a specific prescription")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionReviewDto> findByPrescriptionId(@PathVariable Long prescriptionId) {
        PrescriptionReviewDto review = reviewService.findByPrescriptionId(prescriptionId);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Deletes a prescription review by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

