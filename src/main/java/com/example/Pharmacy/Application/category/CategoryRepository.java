package main.java.com.example.Pharmacy.Application.category;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryId(Long categoryId);
    boolean existsCategoryByProductsProductId(Long productId);
    boolean existsCategoryByCategoryName(String categoryName);
    List<Category> findByProductsProductId(Long productId);
    Optional<Category> findCategoryByCategoryName(String categoryName);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Category c SET c.imageUri = ?1 WHERE c.categoryId = ?2")
    int updateImageUri(String imageUri, Long categoryId);
}
