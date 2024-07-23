package main.java.com.example.Pharmacy.Application.category;

import main.java.com.example.Pharmacy.Application.product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class CategoryDTOMapper implements Function<Category, CategoryDTO> {

    @Override
    public CategoryDTO apply(Category category) {
        List<ProductDTO> productDTOS = category.getProducts()
                .stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getImageUri(),
                        //product.getCategory().getCategoryId(),
                        product.getCategory().getCategoryName(),
                        product.isAvailable()
                ))
                .toList();

        return new CategoryDTO(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDescription(),
                category.getImageUri(),
                productDTOS
        );
    }
}
