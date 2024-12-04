package com.brytech.pharmacist_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.model.Inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryDao {

   Inventory save(Inventory inventory);
   Optional<Inventory> findByProductId(Long productId);
   List<Inventory> findByPharmacyBranchId(Long branchId);
//   List<Inventory>findLowStockByBranchId(Long branchId, int threshold);
   int updateStockByProductId(Long productId, int quantity);
   void deleteById(Long id);
   boolean existsByProductIdAndQuantityGreaterThan(Long productId, int quantity);
   int incrementStockByProductId(Long productId, int change);
   int decrementStockByProductId(Long productId, int change);
   Page<Inventory> findByPharmacyBranchId(Long branchId, Pageable pageable);
}

