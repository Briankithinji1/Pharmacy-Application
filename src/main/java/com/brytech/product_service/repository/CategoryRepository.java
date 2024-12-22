package com.brytech.product_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.product_service.model.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    List<Category> findByNameContaining(String name);
    Page<Category> findByParentCategory(Category parentCategory, Pageable pageable);
}
