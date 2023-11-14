package com.example.Pharmacy.Application.product;

import com.example.Pharmacy.Application.category.Category;
import com.example.Pharmacy.Application.category.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    // given - when - then

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(
                1L,
                "Medicines",
                "Medicines for all kinds of diseases.",
                "medicines.jpg"
        );
        categoryRepository.save(category);

//        products = new Products(); //initialize the product
//        products.setProductId(1L);
//        products.setProductName("Ventolin");
//        products.setDescription("A bronchodilator used to treat asthma and other breathing problems.");
//        products.setImageUri("ventolin.jpg");
//        products.setPrice("19.99");
//        products.setQuantity("50 tablets");
//        products.setCategory(category);
//        products.setAvailable(true);
//        productRepository.save(products);
    }

    @AfterEach
    void tearDown() {
        product = null;
        category = null;
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testFindProductByCategoryId() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Ventolin");
        product.setDescription("A bronchodilator used to treat asthma and other breathing problems.");
        product.setImageUri("ventolin.jpg");
        product.setPrice("19.99");
        product.setQuantity("50 tablets");
        product.setCategory(categoryRepository.findById(1L).orElse(null));
        product.setAvailable(true);
        productRepository.save(product);

        List<Product> productList = productRepository.findByCategoryCategoryId(1L);
        assertThat(productList.get(0).getProductId()).isEqualTo(product.getProductId());
        assertThat(productList.get(0).getProductName()).isEqualTo(product.getProductName());
        assertThat(productList.get(0).getCategory().getCategoryId())
                .isEqualTo(product.getCategory().getCategoryId());
    }

    // Test case FAIL
    @Test
    void testFindProductByCategoryIdFail() {
        List<Product> productList = productRepository.findByCategoryCategoryId(2L);
        assertThat(productList.isEmpty()).isTrue();
    }
}
