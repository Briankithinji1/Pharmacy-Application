package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    boolean existsByPrescriptionId(Long prescriptionId);
    boolean existsByCustomerUserId(Long userId);
    boolean existsByMedicineProductId(Long productId);
    List<Prescription> findAllByCustomerUserId(Long userId);
    List<Prescription> findByMedicineProductId(Long productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Prescription p SET p.prescriptionFileId = ?1 WHERE p.prescriptionId = ?2")
    int updatePrescriptionFileId(String prescriptionFileId, Long prescriptionId);
}
