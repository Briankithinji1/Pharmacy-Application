package com.example.Pharmacy.Application.product;

import com.example.Pharmacy.Application.category.Category;
import com.example.Pharmacy.Application.category.CategoryDao;
import com.example.Pharmacy.Application.category.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;


class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductDTOMapper productDTOMapper;
    @Mock
    private ProductDao productDao;
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;
    AutoCloseable autoCloseable;
    Product product;
    Product updatedProduct;
    Category category;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductService(productDTOMapper, productDao, categoryDao);
        category = new Category(
                1L,
                "Medicines",
                "Medicines for all kinds of diseases.",
                "medicines.jpg"
        );
        categoryRepository.save(category);

        product = new Product(); //initialize the product
        product.setProductId(1L);
        product.setProductName("Ventolin");
        product.setDescription("A bronchodilator used to treat asthma and other breathing problems.");
        product.setImageUri("ventolin.jpg");
//        product.setPrice("19.99");
        product.setQuantity("50 tablets");
        product.setCategory(category);
        product.setAvailable(true);
        productRepository.save(product);

        updatedProduct = new Product(); //initialize the product
        updatedProduct.setProductId(1L);
        updatedProduct.setProductName("TriHistamine");
        updatedProduct.setDescription("A bronchodilator used to treat asthma and other breathing problems.");
        updatedProduct.setImageUri("ventolin.jpg");
//        updatedProduct.setPrice("19.99");
        updatedProduct.setQuantity("50 tablets");
        updatedProduct.setCategory(category);
        updatedProduct.setAvailable(true);
        productRepository.save(updatedProduct);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllProducts() {
        // when
        productService.getAllProducts();
        // then
        verify(productDao).selectAllProducts();
    }

    @Test
    void getProductById() {

    }

    @Test
    void getProductByCategory() {
    }

    @Test
    void testAddProduct() {
        mock(Product.class);
        mock(Category.class);
        mock(CategoryRepository.class);
        mock(ProductDao.class);
        mock(ProductDTOMapper.class);

        when(productDao.existsProductByName(product.getProductName())).thenReturn(false);

        productService.addProduct(product);

        verify(productDao, times(1)).insertProduct(product);
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void testUpdateProduct() {
        mock(Product.class);
        mock(Category.class);
        mock(CategoryRepository.class);
        mock(ProductDao.class);

        when(productDao.selectProductById(product.getProductId())).thenReturn(Optional.of(product));
        when(productDao.existsProductByName(updatedProduct.getProductName())).thenReturn(false);

        productService.updateProduct(product.getProductId(), updatedProduct);

        verify(productDao, times(1)).updateProduct(updatedProduct);
    }


    @Test
    void updateProductPrice() {
    }

    @Test
    void updateProductQuantity() {
    }
}