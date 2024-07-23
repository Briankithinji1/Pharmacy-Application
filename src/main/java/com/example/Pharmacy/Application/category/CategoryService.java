package main.java.com.example.Pharmacy.Application.category;

import main.java.com.example.Pharmacy.Application.exception.RequestValidationException;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryDTOMapper categoryDTOMapper;

    public CategoryService(CategoryDao categoryDao, CategoryDTOMapper categoryDTOMapper) {
        this.categoryDao = categoryDao;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryDao.selectAllCategories()
                .stream()
                .map(categoryDTOMapper)
                .toList();
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        return categoryDao.selectCategoryById(categoryId)
                .map(categoryDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with id [%s] not found".formatted(categoryId)
                ));
    }

    public CategoryDTO getCategoryByName(String categoryName) {
        return categoryDao.selectCategoryByCategoryName(categoryName)
                .map(categoryDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Category with categoryName [%s] not found", categoryName)
                ));
    }

    public List<CategoryDTO> getCategoryByProductId(Long productId) {
        if (!categoryDao.existsCategoryByProductId(productId)) {
            throw new ResourceNotFoundException(
                    "Category not found for productId: [%s]".formatted(productId)
            );
        }

        return categoryDao.selectAllCategoriesByProductId(productId)
                .stream()
                .map(categoryDTOMapper)
                .toList();
    }

    public void addCategory (Category category) {
        if (categoryDao.existsCategoryByCategoryName(category.getCategoryName())) {
            throw new ResourceNotFoundException(
                    "Category already exists"
            );
        }
        categoryDao.insertCategory(category);
    }

    public void deleteCategory (Long categoryId) {
        if (!categoryDao.existsCategoryById(categoryId)) {
            throw new ResourceNotFoundException(
                    "Category with id [%s] not found".formatted(categoryId)
            );
        }
        categoryDao.deleteCategory(categoryId);
    }

    public void updateCategory (Long categoryId, Category category) {
        Category categories = categoryDao.selectCategoryById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with id [%s] not found".formatted(categoryId)
                ));

        boolean changes = true;

        if (category.getCategoryName() != null && !category.getCategoryName().equals(categories.getCategoryName())) {
            categories.setCategoryName(category.getCategoryName());
            changes = true;
        }

        if (category.getDescription() != null && !category.getDescription().equals(categories.getDescription())) {
            categories.setDescription(category.getDescription());
            changes = true;
        }

        if (category.getProducts() != null && !category.getProducts().equals(categories.getProducts())) {
            categories.setProducts(category.getProducts());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes were made"
            );
        }

        categoryDao.updateCategory(category);
    }

    public void uploadImageUri (String imageUri, Long categoryId) {
        // TODO: Handle upload logic

        categoryDao.updateCategoryImageUri(imageUri, categoryId);
    }

    // TODO: Handle logic/method to get the prescription file
}
