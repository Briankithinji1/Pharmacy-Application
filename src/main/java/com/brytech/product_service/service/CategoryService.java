package com.brytech.product_service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.brytech.product_service.dao.CategoryDao;
import com.brytech.product_service.dto.CategoryDto;
import com.brytech.product_service.event.ProductCategoryAddedEvent;
import com.brytech.product_service.event.ProductCategoryRemovedEvent;
import com.brytech.product_service.event.ProductEventPublisher;
import com.brytech.product_service.exception.RequestValidationException;
import com.brytech.product_service.exception.ResourceNotFoundException;
import com.brytech.product_service.model.Category;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ModelMapper mapper;
    private final ProductEventPublisher productEventPublisher;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryDto.name() == null || categoryDto.name().trim().isEmpty()) {
            throw new RequestValidationException("Categoy name cannot be null or empty");
        }

        Category category = mapper.map(categoryDto, Category.class);

        if (categoryDto.parentCategoryId() != null) {
            Category parentCategory = categoryDao.findById(categoryDto.parentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                            "No parent category exists with the specified ID: " + categoryDto.parentCategoryId()
                ));

            parentCategory.addSubCategory(category);
        }

        category = categoryDao.save(category);

        ProductCategoryAddedEvent event = new ProductCategoryAddedEvent(
                category.getCategoryId(), 
                categoryDto.parentCategoryId(),
                Instant.now()
        );
        productEventPublisher.publishProductCategoryAddedEvent(event);

        return convertToDto(category);
    }

    public CategoryDto findCategoryById(Long id) {
        return categoryDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with ID [%s] not found".formatted(id)
            ));
    }

    public CategoryDto findCategoryByName(String name) {
        return categoryDao.findByName(name)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with name [%s] not found".formatted(name)
            ));
    }

    public List<CategoryDto> findCategoryByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RequestValidationException("Name cannot be null or emtpy");
        }

        return categoryDao.findByNameContaining(name)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public Page<CategoryDto> findAllCategories(Pageable pageable) {
        return categoryDao.findAll(pageable)
            .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findCategoriesByParentCategory(Long parentCategoryId) {
        Category parentCategory = categoryDao.findById(parentCategoryId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Parent category with ID [%s] not found".formatted(parentCategoryId)
            ));

        List<Category> subCategories = new ArrayList<>(parentCategory.getSubCategories());

        return subCategories.stream()
            .map(this::convertToDto)
            .toList();
    }
    
    @Transactional
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryDao.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        if (categoryDto.name() != null && categoryDto.name().trim().isEmpty()) {
            throw new RequestValidationException("Category name cannot be empty");
        }

        if (categoryDto.name() != null) {
            existingCategory.setName(categoryDto.name());
        }

        if (categoryDto.imageUrl() != null) {
            existingCategory.setImageUrl(categoryDto.imageUrl());
        }

        if (categoryDto.description() != null) {
            existingCategory.setDescription(categoryDto.description());
        }

        // Update parent category if parentCategoryId is provided
        if (categoryDto.parentCategoryId() != null) {
            if (categoryDto.parentCategoryId().equals(existingCategory.getCategoryId())) {
                throw new RequestValidationException("A category cannot be its own parent");
            }

            Category newParentCategory = categoryDao.findById(categoryDto.parentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                            "Parent category not found with ID: " + categoryDto.parentCategoryId()));

        
            // Remove from the old parent if any
            if (existingCategory.getParentCategory() != null) {
                existingCategory.getParentCategory().removeSubCategory(existingCategory);
            }

            // Add to the new parent
            newParentCategory.addSubCategory(existingCategory);
        } else {
            // If null, remove the existing parent
            if (existingCategory.getParentCategory() != null) {
                existingCategory.getParentCategory().removeSubCategory(existingCategory);
                existingCategory.setParentCategory(null);
            }
        }

        Category updatedCategory = categoryDao.save(existingCategory);

        return convertToDto(updatedCategory);
    }

    public void removeCategory(Long id) {
        Category category = categoryDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with ID [%s] not found".formatted(id)
            ));

        Long parentCategoryId = category.getParentCategory() != null
            ? category.getParentCategory().getCategoryId()
            : null;

        categoryDao.delete(id);

        ProductCategoryRemovedEvent event = new ProductCategoryRemovedEvent(
                id, 
                parentCategoryId, 
                Instant.now()
        );
        productEventPublisher.publishProductCategoryRemovedEvent(event);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategoriesByIds(List<Long> categoryIds) {
        List<Category> categories = categoryDao.findAllById(categoryIds);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No categories found for the provided IDs: " + categoryIds
            );
        }

        return categories.stream()
            .map(this::convertToDto)
            .toList();
    }

    private CategoryDto convertToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }
}
