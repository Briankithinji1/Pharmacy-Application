package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.exception.RequestValidationException;
import com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionDao prescriptionDao;
    private final PrescriptionDTOMapper dtoMapper;

    public PrescriptionService(PrescriptionDao prescriptionDao, PrescriptionDTOMapper dtoMapper) {
        this.prescriptionDao = prescriptionDao;
        this.dtoMapper = dtoMapper;
    }

    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionDao.selectAllPrescriptions()
                .stream()
                .map(dtoMapper)
                .toList();
    }

    public PrescriptionDTO getPrescriptionById(Long prescriptionId) {
        return prescriptionDao.selectPrescriptionById(prescriptionId)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription with id [%s] not found".formatted(prescriptionId)
                ));
    }

    public List<PrescriptionDTO> getPrescriptionByUserId(Long userId) {
        if (!prescriptionDao.isPrescriptionExistByUserId(userId)) {
            throw new ResourceNotFoundException(
                    "Prescription not found for userId: [%s]".formatted(userId)
            );
        }

        return prescriptionDao.selectAllPrescriptionsByUserId(userId)
                .stream()
                .map(dtoMapper)
                .toList();
    }

//    public List<PrescriptionDTO> getPrescriptionByProductId(Long productId) {
//        if (!prescriptionDao.isPrescriptionExistByProductId(productId)) {
//            throw new ResourceNotFoundException(
//                    "Prescription not found for productId: [%s]".formatted(productId)
//            );
//        }
//
//        return prescriptionDao.selectAllPrescriptionsByProductId(productId)
//                .stream()
//                .map(dtoMapper)
//                .toList();
//    }

    public void addPrescription (Prescription prescription) {
        if (prescriptionDao.isPrescriptionExistById(prescription.getPrescriptionId())) {
            throw new ResourceNotFoundException("Prescription already exists");
        }
        prescriptionDao.insertPrescription(prescription);
    }

    public void deletePrescription (Long prescriptionId) {
        if (!prescriptionDao.isPrescriptionExistById(prescriptionId)) {
            throw new ResourceNotFoundException(
                    "Prescription with id [%s] not found".formatted(prescriptionId)
            );
        }
        prescriptionDao.deletePrescription(prescriptionId);
    }

    public void updatePrescription (Long prescriptionId, Prescription prescription) {
        Prescription prescriptions = prescriptionDao.selectPrescriptionById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription with id [%s] not found".formatted(prescriptionId)
                ));

        boolean changes = false;

        if (prescription.getDosage() != null && !prescription.getDosage().equals(prescriptions.getDosage())) {
            prescriptions.setDosage(prescription.getDosage());
            changes = true;
        }

        if (prescription.getInstructionsOfUse() != null && !prescription.getInstructionsOfUse().equals(prescriptions.getInstructionsOfUse())) {
            prescriptions.setInstructionsOfUse(prescription.getInstructionsOfUse());
            changes = true;
        }

        if (prescription.getUsageDuration() != null && !prescription.getUsageDuration().equals(prescriptions.getUsageDuration())) {
            prescriptions.setUsageDuration(prescription.getUsageDuration());
            changes = true;
        }

        if (prescription.getCustomer() != null && !prescription.getCustomer().equals(prescriptions.getCustomer())) {
            prescriptions.setCustomer(prescription.getCustomer());
            changes = true;
        }

        if (prescription.getProduct() != null && !prescription.getProduct().equals(prescriptions.getProduct())) {
            prescriptions.setProduct(prescription.getProduct());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes were made"
            );
        }

        prescriptionDao.updatePrescription(prescription);
    }

    public void uploadPrescriptionFileId (String prescriptionFileId, Long prescriptionId) {
        // TODO: Handle upload logic

        prescriptionDao.updatePrescriptionPrescriptionFileId(prescriptionFileId, prescriptionId);
    }

    // TODO: Handle logic/method to get the prescription file
}
