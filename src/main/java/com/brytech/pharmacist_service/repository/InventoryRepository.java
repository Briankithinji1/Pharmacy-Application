package com.brytech.pharmacist_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.model.Inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByPharmacyBranchId(Long branchId);

    boolean existsByProductIdAndQuantityGreaterThan(Long productId, int quantity);

    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = :quantity WHERE i.product.id = :productId")
    int updateStockByProductId(@Param("productId") Long productId, @Param("quantity") int quantity);

    Optional<Inventory> findByProductId(Long productId);

    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :change WHERE i.product.id = :productId")
    int incrementStockByProductId(@Param("productId") Long productId, @Param("change") int change);

    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity - :change WHERE i.product.id = :productId AND i.quantity >= :change")
    int decrementStockByProductId(@Param("productId") Long productId, @Param("change") int change);

    Page<Inventory> findByPharmacyBranchId(Long branchId, Pageable pageable);
}
