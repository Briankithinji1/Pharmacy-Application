package main.java.com.example.Pharmacy.Application.prescription;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {
    // Basic CRUD operations
    List<Prescription> selectAllPrescriptions();
    Optional<Prescription> selectPrescriptionById(Long prescriptionId);
    List<Prescription> selectAllPrescriptionsByUserId(Long userId);
    void insertPrescription(Prescription prescription);
    void updatePrescription(Prescription prescription);
    void deletePrescription(Long prescriptionId);

    // Existence checks
    boolean isPrescriptionExistById(Long prescriptionId);
    boolean isPrescriptionExistByUserId(Long userId);

    // Additional Operations
    void updatePrescriptionPrescriptionFileId(String prescriptionFileId, Long prescriptionId);
    List<Prescription> selectPrescriptionsByStatus(PrescriptionStatus status);
    List<Prescription> selectPrescriptionsByDateRange(Instant startDate, Instant endDate);
    void updatePrescriptionStatus(Long prescriptionId, PrescriptionStatus status);
}
