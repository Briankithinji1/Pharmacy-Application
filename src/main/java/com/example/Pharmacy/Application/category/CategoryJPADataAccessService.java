package com.example.Pharmacy.Application.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("categoryJPA")
public class CategoryJPADataAccessService implements CategoryDao {

    private final CategoryRepository repository;

    public CategoryJPADataAccessService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> selectAllCategories() {
        Page<Category> page = repository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Category> selectCategoryById(Long categoryId) {
        return repository.findById(categoryId);
    }

    @Override
    public List<Category> selectAllCategoriesByProductId(Long productId) {
        return repository.findByProductsProductId(productId);
    }

    @Override
    public Optional<Category> selectCategoryByCategoryName(String categoryName) {
        return repository.findCategoryByCategoryName(categoryName);
    }

    @Override
    public void insertCategory(Category category) {
        repository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        repository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        repository.deleteById(categoryId);
    }

    @Override
    public boolean existsCategoryById(Long categoryId) {
        return repository.existsByCategoryId(categoryId);
    }

    @Override
    public boolean existsCategoryByProductId(Long productId) {
        return repository.existsCategoryByProductsProductId(productId);
    }

    @Override
    public boolean existsCategoryByCategoryName(String name) {
        return repository.existsCategoryByCategoryName(name);
    }

    @Override
    public void updateCategoryImageUri(String imageUri, Long categoryId) {
        repository.updateImageUri(imageUri, categoryId);
    }
}
