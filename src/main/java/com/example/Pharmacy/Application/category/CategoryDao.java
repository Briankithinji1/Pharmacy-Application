package main.java.com.example.Pharmacy.Application.category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> selectAllCategories();
    Optional<Category> selectCategoryById(Long categoryId);
    List<Category> selectAllCategoriesByProductId(Long productId);
    Optional<Category> selectCategoryByCategoryName(String categoryName);
    void insertCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long categoryId);
    boolean existsCategoryById(Long categoryId);
    boolean existsCategoryByProductId(Long productId);
    boolean existsCategoryByCategoryName(String categoryName);
    void updateCategoryImageUri(String imageUri, Long categoryId);
}
