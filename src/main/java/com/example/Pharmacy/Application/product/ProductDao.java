package main.java.com.example.Pharmacy.Application.product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> selectAllProducts();
    List<Product> selectProductsByCategoryId(Long categoryId);
    Optional<Product> selectProductById(Long productId);
    void insertProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long productId);
    boolean isProductExistsById(Long productId); // should also check the availability and quantity
    boolean existsProductByName(String productName);
    void updateProductQuantity(Long productId, int quantity); // should also update the availability
    void updateProductAvailability(Long productId, boolean isAvailable);
    void updateProductPrice(Long productId, String price);
    void updateProductImage(Long productId, String imageUri);
}
