package com.brytech.product_service.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.brytech.product_service.model.Category;
import com.brytech.product_service.repository.CategoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("categoryJpa")
public class CategoryJpaDataAccessService implements CategoryDao {

    private final CategoryRepository categoryRepository;

    public CategoryJpaDataAccessService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findByNameContaining(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findByParentCategory(Category parentCategory, Pageable pageable) {
        return categoryRepository.findByParentCategory(parentCategory, pageable);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAllById(Collection<Long> ids) {
        return categoryRepository.findAllById(ids);
    } 
}
