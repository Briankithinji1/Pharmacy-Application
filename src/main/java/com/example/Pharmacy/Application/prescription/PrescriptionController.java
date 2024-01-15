package com.example.Pharmacy.Application.prescription;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/byId/{prescriptionId}")
    public PrescriptionDTO getPrescriptionById(
            @PathVariable("prescriptionId") Long prescriptionId
    ) {
        return prescriptionService.getPrescriptionById(prescriptionId);
    }

    @GetMapping("allPrescriptions")
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("{userId}")
    public List<PrescriptionDTO> getPrescriptionByUserId(
            @PathVariable("userId") Long userId
    ) {
        return prescriptionService.getPrescriptionByUserId(userId);
    }

    @GetMapping("/byProductId/{productId}")
    public List<PrescriptionDTO> getPrescriptionByProductId(
            @PathVariable("prescriptionId") Long productId
    ) {
        return prescriptionService.getPrescriptionByProductId(productId);
    }

    @PostMapping("addPrescription")
    public ResponseEntity<?> addPrescription(
            @RequestBody Prescription prescription
    ) {
        prescriptionService.addPrescription(prescription);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{prescriptionId}/delete")
    public void deleteOrder(
            @PathVariable("prescriptionId") Long prescriptionId
    ) {
        prescriptionService.deletePrescription(prescriptionId);
    }

    @PutMapping("{prescriptionId}/update")
    public void updatePrescription(
            @PathVariable("prescriptionId") Long prescriptionId,
            @RequestBody Prescription prescription
    ) {
        prescriptionService.updatePrescription(prescriptionId, prescription);
    }

    // TODO: Handle uploading of prescription file

    // TODO: Handle getting the prescription file
}
