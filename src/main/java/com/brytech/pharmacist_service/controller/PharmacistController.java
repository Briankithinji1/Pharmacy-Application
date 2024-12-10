package com.brytech.pharmacist_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.brytech.pharmacist_service.dto.PharmacistDto;
import com.brytech.pharmacist_service.service.PharmacistService;

import org.apache.kafka.common.requests.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacist")
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @GetMapping("/{pharmacistId}")
    @Operation(summary = "Find by pharmacist ID", description = "Retrieves a pharmacist by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pharmacist retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PharmacistDto.class))),
            @ApiResponse(responseCode = "404", description = "Pharmacist not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PharmacistDto> findByPharmacistId (@PathVariable("pharmacistId") Long pharmacistId) {
        PharmacistDto pharmacist = pharmacistService.findByPharmacistId(pharmacistId);
        return ResponseEntity.ok(pharmacist);
    }

    @GetMapping("/search")
    @Operation(summary = "Search pharmacists", description = "Searches for a pharmacist by email or phone number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pharmacist successfully retrieved",
                    content = @Content(schema = @Schema(implementation = PharmacistDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Pharmacist not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PharmacistDto> searchPharmacist(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber
    ) {
        PharmacistDto pharmacist = pharmacistService.findByEmailOrPhoneNumber(email, phoneNumber);
        return ResponseEntity.ok(pharmacist);
    }
 
    @GetMapping
    @Operation(summary = "Get all pharmacists", description = "Retrieves all pharmacists")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pharmacists retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PharmacistDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PharmacistDto>> getAllPharmacists() {
        List<PharmacistDto> pharmacists = pharmacistService.findAllPharmacist();
        return ResponseEntity.ok(pharmacists);
    }

    @PutMapping("/{pharmacistId}")
    @Operation(summary = "Update pharmacist", description = "Updates the pharmacist's profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pharmacist profile updated successfully",
                    content = @Content(schema = @Schema(implementation = PharmacistDto.class))),
            @ApiResponse(responseCode = "404", description = "Pharmacist not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PharmacistDto> updatePharmacist(
            @PathVariable("pharmacistId") Long pharmacistId,
            @RequestBody @Valid PharmacistDto pharmacistDto
    ) {
        PharmacistDto updatedPharmacist = pharmacistService.updatePharmacist(pharmacistId, pharmacistDto);
        return ResponseEntity.ok(updatedPharmacist);
    }

    @DeleteMapping("/{pharmacistId}")
    @Operation(summary = "Delete pharmacist", description = "Deletes the pharmacist's profile")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pharmacist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pharmacist not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deletePharmacist(@PathVariable("pharmacistId") Long pharmacistId) {
        pharmacistService.deleteById(pharmacistId);
        return ResponseEntity.noContent().build();
    }
}
