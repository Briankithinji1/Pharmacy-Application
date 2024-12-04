package com.brytech.pharmacist_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.model.Inventory;
import com.brytech.pharmacist_service.repository.InventoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("inventoryJpa")
public class InventoryJpaDataAccessService implements InventoryDao {

    private final InventoryRepository inventoryRepository;

    public InventoryJpaDataAccessService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> findByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public List<Inventory> findByPharmacyBranchId(Long branchId) {
        return inventoryRepository.findByPharmacyBranchId(branchId);
    }

    @Override
    public int updateStockByProductId(Long productId, int quantity) {
        return inventoryRepository.updateStockByProductId(productId, quantity);
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByProductIdAndQuantityGreaterThan(Long productId, int quantity) {
        return inventoryRepository.existsByProductIdAndQuantityGreaterThan(productId, quantity);
    }

    @Override
    public int incrementStockByProductId(Long productId, int change) {
        return inventoryRepository.incrementStockByProductId(productId, change);
    }

    @Override
    public int decrementStockByProductId(Long productId, int change) {
        return inventoryRepository.decrementStockByProductId(productId, change);
    }

    @Override
    public Page<Inventory> findByPharmacyBranchId(Long branchId, Pageable pageable) {
        return inventoryRepository.findByPharmacyBranchId(branchId, pageable);
    }

    
}
