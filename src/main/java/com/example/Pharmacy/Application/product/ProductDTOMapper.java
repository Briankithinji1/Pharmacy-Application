package com.example.Pharmacy.Application.product;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {

        @Override
        public ProductDTO apply(Product product) {
            return new ProductDTO(
                    product.getProductId(),
                    product.getProductName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getImageUri(),
                    //product.getCategory().getCategoryId(),
                    product.getCategory().getCategoryName(),
                    product.isAvailable()
            );
        }
}
