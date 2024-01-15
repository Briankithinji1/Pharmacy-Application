package com.example.Pharmacy.Application.prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {
    List<Prescription> selectAllPrescriptions();
    Optional<Prescription> selectPrescriptionById(Long prescriptionId);
    List<Prescription> selectAllPrescriptionsByUserId(Long userId);
//    List<Prescription> selectAllPrescriptionsByProductId(Long productId);
    void insertPrescription(Prescription prescription);
    void updatePrescription(Prescription prescription);
    void deletePrescription(Long prescriptionId);
    boolean isPrescriptionExistById(Long prescriptionId);
    boolean isPrescriptionExistByUserId(Long userId);
//    boolean isPrescriptionExistByProductId(Long productId);
    void updatePrescriptionPrescriptionFileId(String prescriptionFileId, Long prescriptionId);
}
