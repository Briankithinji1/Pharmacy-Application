package main.java.com.example.Pharmacy.Application.product;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsProductByProductId(Long productId);
    boolean existsProductByProductName(String productName);

    List<Product> findByCategoryCategoryId(Long categoryId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.imageUri = :imageUri WHERE p.id = :productId")
    int updateProductImageById(@Param("imageUri") String imageUri, @Param("productId") Long productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.price = :price WHERE p.id = :productId")
    int updateProductPriceById(@Param("price") String price, @Param("productId") Long productId);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Products p SET p.quantity = ?1 WHERE p.productId = ?2")
//    int updateProductQuantityById(String quantity, Long productId);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = :newQuantity WHERE p.id = :productId")
    int updateProductQuantityById(@Param("productId") Long productId, @Param("newQuantity") int newQuantity);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.isAvailable = :available WHERE p.id = :productId")
    int updateProductAvailabilityById(@Param("available") boolean isAvailable, @Param("productId") Long productId);
}
