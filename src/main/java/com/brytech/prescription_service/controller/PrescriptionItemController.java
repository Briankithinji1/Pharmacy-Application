package com.brytech.prescription_service.controller;

import com.brytech.prescription_service.dto.PrescriptionItemDTO;
import com.brytech.prescription_service.exceptions.ApiError;
import com.brytech.prescription_service.service.PrescriptionItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/prescription_items")
@Tag(name = "Prescription Items", description = "Manage prescription items")
@RequiredArgsConstructor
public class PrescriptionItemController {

    private final PrescriptionItemService itemService;

    @PostMapping("/items/bulk")
    @Operation(summary = "Create multiple prescription items", description = "Adds a batch of items to the specified prescription.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Items successfully created",
                    content = @Content(schema = @Schema(implementation = PrescriptionItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate resources",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionItemDTO>> saveItems(
            @RequestBody List<PrescriptionItemDTO> items
    ) {
        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<PrescriptionItemDTO> savedItems = itemService.saveAll(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItems);
    }

    @PostMapping("/item")
    @Operation(summary = "Create a new prescription item", description = "Adds a new item to the specified prescription.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item successfully created",
                    content = @Content(schema = @Schema(implementation = PrescriptionItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate resource",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionItemDTO> saveItem(
            @RequestBody PrescriptionItemDTO item
    ) {
        PrescriptionItemDTO savedItem = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @GetMapping("/prescriptions/{id}/items")
    @Operation(summary = "Find items by prescription ID", description = "Retrieves all items associated with a specific prescription, with optional pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Items successfully retrieved",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid prescription ID",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<PrescriptionItemDTO>> getItemsByPrescriptionId(
            @PathVariable("id") Long prescriptionId,
            Pageable pageable
    ) {
        Page<PrescriptionItemDTO> items = itemService.findByPrescriptionId(prescriptionId, pageable);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items")
    @Operation(summary = "Find items by name and prescription ID", description = "Retrieves items associated with a specific prescription")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid item name",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid prescription ID",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<PrescriptionItemDTO>> findByNameAndPrescriptionId(
            @RequestParam String name,
            @RequestParam Long prescriptionId
    ) {
        List<PrescriptionItemDTO> items = itemService.findByNameAndPrescriptionId(name, prescriptionId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Update a prescription item", description = "Updates details of an existing prescription item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item successfully updated",
                    content = @Content(schema = @Schema(implementation = PrescriptionItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<PrescriptionItemDTO> updateItem(
            @PathVariable("id") Long id,
            @Valid @RequestBody PrescriptionItemDTO updatedItem
    ) {
        PrescriptionItemDTO savedItem = itemService.updateItem(id, updatedItem);
        return ResponseEntity.ok(savedItem);
    }

    @DeleteMapping("items/{id}")
    @Operation(summary = "Delete a prescription item by ID", description = "Deletes a single prescription item by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid item ID",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteItemById(
            @PathVariable("id") Long id
    ) {
        itemService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/prescriptions/{prescriptionId}/items")
    @Operation(summary = "Delete a prescription item by associated prescription ID", description = "Deletes a single prescription item by its associated prescription ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid item ID",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteItemsByPrescriptionId(
            @PathVariable("prescriptionId") Long prescriptionId
    ) {
        itemService.deleteByPrescriptionId(prescriptionId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

//    @GetMapping("/list")
//    @Operation(summary = "Find all items by prescription ID", description = "Retrieves all items for a prescription without pagination.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Items successfully retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PrescriptionItemDTO.class)))),
//            @ApiResponse(responseCode = "400", description = "Invalid prescription ID", content = @Content(schema = @Schema(implementation = ApiError.class)))
//    })
//    public ResponseEntity<List<PrescriptionItemDTO>> getItemsByPrescriptionId(@RequestParam Long prescriptionId) {
//        List<PrescriptionItemDTO> items = prescriptionItemService.findByPrescriptionId(prescriptionId);
//        return ResponseEntity.ok(items);
//    }

}
