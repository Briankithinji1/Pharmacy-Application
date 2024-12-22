package com.brytech.product_service.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.brytech.product_service.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryDao {

    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    List<Category> findByNameContaining(String name);
    List<Category> findAll();
    List<Category> findAllById(Collection<Long> ids);
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByParentCategory(Category parentCategory, Pageable pageable);
    Category update(Category category);
    void delete(Long id);
}
