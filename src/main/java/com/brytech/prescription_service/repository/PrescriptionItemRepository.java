package com.brytech.prescription_service.repository;

import com.brytech.prescription_service.models.PrescriptionItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {

   List<PrescriptionItem> findByMedicineNameAndPrescriptionId(String name, Long prescriptionId);
   Page<PrescriptionItem> findByPrescriptionId(Long prescriptionId, Pageable pageable);
   List<PrescriptionItem> findByPrescriptionId(Long prescriptionId); // Optional
   void deleteByPrescriptionId(Long prescriptionId);

   boolean existsByMedicineNameAndPrescriptionId(String name, Long prescriptionId);
   long countByPrescriptionId(Long prescriptionId);

   @Query("SELECT i FROM PrescriptionItem i WHERE i.prescription.id = :prescriptionId ORDER BY i.medicineName ASC")
   List<PrescriptionItem> findSortedByPrescriptionId(@Param("prescriptionId") Long prescriptionId);
}
