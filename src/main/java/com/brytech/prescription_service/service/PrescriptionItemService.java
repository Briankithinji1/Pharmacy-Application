package com.brytech.prescription_service.service;

import com.brytech.prescription_service.dao.PrescriptionItemDao;
import com.brytech.prescription_service.dto.PrescriptionItemDTO;
import com.brytech.prescription_service.exceptions.DuplicateResourceException;
import com.brytech.prescription_service.exceptions.RequestValidationException;
import com.brytech.prescription_service.exceptions.ResourceNotFoundException;
import com.brytech.prescription_service.models.Prescription;
import com.brytech.prescription_service.models.PrescriptionItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PrescriptionItemService {

    private final PrescriptionItemDao itemDao;
    private final ModelMapper mapper;

    @Transactional
    public List<PrescriptionItemDTO> saveAll(List<PrescriptionItemDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new RequestValidationException("Items list cannot be null or empty");
        }

        Set<String> uniqueItems = new HashSet<>();
        for (int i = 0; i < items.size(); i++) {
            PrescriptionItemDTO item = items.get(i);
            String name = item.medicineName();
            Long prescriptionId = item.prescription().id();

            if (name == null || name.isBlank()) {
                throw new RequestValidationException("Item name at index " + i + " cannot be null or blank");
            }

            if (prescriptionId == null) {
                throw new RequestValidationException("Prescription ID at index " + i + " cannot be null");
            }

            if (!uniqueItems.add(name + ":" + prescriptionId)) {
                throw new DuplicateResourceException("Duplicate items found for the same prescription: " + name);
            }
        }

//        // Validate each item in the list
//        for (PrescriptionItemDTO item : items) {
//            if (item.medicineName() == null || item.medicineName().isBlank()) {
//                throw new RequestValidationException("Item name cannot be null or blank");
//            }
//            if (item.prescription() == null || item.prescription().id() == null) {
//                throw new RequestValidationException("Each item must be associated with a valid prescription");
//            }
//        }
//
//        Map<String, Long> itemNames = new HashMap<>();
//        for (PrescriptionItemDTO item : items) {
//            String name = item.medicineName();
//            Long prescriptionId = item.prescription().id();
//            if (itemNames.containsKey(name) && itemNames.get(name).equals(prescriptionId)) {
//                throw new DuplicateResourceException("Duplicate items found for the same prescription: " + name);
//            }
//            itemNames.put(name, prescriptionId);
//        }

        // Convert DTO to entity
        List<PrescriptionItem> prescriptionItems = items.stream()
                .map(item -> mapper.map(item, PrescriptionItem.class))
                .toList();

        // Save items
        List<PrescriptionItem> savedItems = itemDao.saveAll(prescriptionItems);

        // Convert saved entity back to DTO
        return savedItems.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public PrescriptionItemDTO saveItem(PrescriptionItemDTO item) {
        if (item == null || item.medicineName() == null || item.medicineName().isBlank()) {
            throw new RequestValidationException("Prescription item must have a valid name");
        }
        if (item.prescription() == null || item.prescription().id() == null) {
            throw new RequestValidationException("Prescription item must be associated with a valid prescription");
        }
        if (itemDao.existsByNameAndPrescriptionId(item.medicineName(), item.prescription().id())) {
            throw new DuplicateResourceException(
                    String.format("Item with the name [%s] already exists for prescription ID %d",
                            item.medicineName(), item.prescription().id())
            );
        }

        PrescriptionItem prescriptionItem = mapper.map(item, PrescriptionItem.class);
        PrescriptionItem savedItem = itemDao.saveItem(prescriptionItem);

        return convertToDTO(savedItem);
    }

    public Page<PrescriptionItemDTO> findByPrescriptionId(Long prescriptionId, Pageable pageable) {
        if (prescriptionId == null) {
            throw new RequestValidationException("Prescription ID must not be null");
        }
        return itemDao.findByPrescriptionId(prescriptionId, pageable)
                .map(this::convertToDTO);
    }

    public List<PrescriptionItemDTO> findByPrescriptionId(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new RequestValidationException("Prescription ID must not be null");
        }
        return itemDao.findByPrescriptionId(prescriptionId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<PrescriptionItemDTO> findByNameAndPrescriptionId(String name, Long prescriptionId) {
        if (name == null || name.isBlank()) {
            throw new RequestValidationException("Item name must not be null or blank");
        }
        if (prescriptionId == null) {
            throw new RequestValidationException("Prescription ID must not be null");
        }
        List<PrescriptionItemDTO> results = itemDao.findByNameAndPrescriptionId(name, prescriptionId)
                .stream()
                .map(this::convertToDTO)
                .toList();

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No items found with name '" + name
                    + "' for prescription ID: " + prescriptionId);
        }
        return results;
    }

    @Transactional
    public PrescriptionItemDTO updateItem(Long id, PrescriptionItemDTO updatedItem) {
        if (id == null) {
            throw new RequestValidationException("Item ID must not be null");
        }

        if (updatedItem == null || updatedItem.medicineName() == null || updatedItem.medicineName().isBlank()) {
            throw new RequestValidationException("Updated item must have a valid name");
        }

        if (updatedItem.prescription() == null || updatedItem.prescription().id() == null) {
            throw new RequestValidationException("Updated item must be associated with a valid prescription");
        }

        // Check if the item exists
        PrescriptionItem existingItem = itemDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription item not found for ID: " + id));

        // Update fields
        existingItem.setMedicineName(updatedItem.medicineName());
        existingItem.setDosage(updatedItem.dosage());
        existingItem.setFrequency(updatedItem.frequency());
        existingItem.setInstructions(updatedItem.instructions());
        existingItem.setQuantity(updatedItem.quantity());
        existingItem.setPrescription(mapper.map(updatedItem.prescription(), Prescription.class));

        // Save and return updated item
        PrescriptionItem savedItem = itemDao.saveItem(existingItem);
        return convertToDTO(savedItem);
    }

    @Transactional
    public void deleteById(Long itemId) {
        if (itemId == null) {
            throw new RequestValidationException("Item ID must not be null");
        }
        if (!itemDao.existsById(itemId)) {
            throw new ResourceNotFoundException("Prescription item not found for ID: " + itemId);
        }
        itemDao.deleteById(itemId);
    }

    public void deleteByPrescriptionId(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new RequestValidationException("Prescription ID must not be null");
        }
        if (itemDao.countByPrescriptionId(prescriptionId) == 0) {
            throw new ResourceNotFoundException("No items found for the given prescription ID: " + prescriptionId);
        }
        itemDao.deleteByPrescriptionId(prescriptionId);
    }

    private PrescriptionItemDTO convertToDTO(PrescriptionItem item) {
        return mapper.map(item, PrescriptionItemDTO.class);
    }
}
