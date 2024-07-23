package main.java.com.example.Pharmacy.Application.product;

import main.java.com.example.Pharmacy.Application.category.Category;
import main.java.com.example.Pharmacy.Application.category.CategoryDao;
import main.java.com.example.Pharmacy.Application.exception.DuplicateResourceException;
import main.java.com.example.Pharmacy.Application.exception.RequestValidationException;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductDTOMapper productDTOMapper;
    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    public ProductService(
            ProductDTOMapper productDTOMapper,
            ProductDao productDao,
            CategoryDao categoryDao) {
        this.productDTOMapper = productDTOMapper;
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    public List<ProductDTO> getAllProducts() {
        return productDao.selectAllProducts()
                .stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long productId) {
        return productDao.selectProductById(productId)
                .map(productDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id [%s] not found".formatted(productId)
                ));
    }

    public List<ProductDTO> getProductByCategory(Long categoryId) {
        return productDao.selectProductsByCategoryId(categoryId)
                .stream()
                .map(productDTOMapper)
                .collect(Collectors.toList());
    }

    public void addProduct(Product product) {
        if (product.getCategory() != null) {
            String categoryName = product.getCategory().getCategoryName();
            Optional<Category> categoryOptional = categoryDao.selectCategoryByCategoryName(categoryName);

            if (categoryOptional.isEmpty()) {
                throw new ResourceNotFoundException("Category with name not found");
            }

            Category category = categoryOptional.get();

            product.setCategory(category);
        } else {
            throw new RequestValidationException("Product must have a valid Category.");
        }

        if (productDao.existsProductByName(product.getProductName())) {
            throw new DuplicateResourceException(
                    "Product already exists");
        }
        productDao.insertProduct(product);
    }

    public void deleteProduct(Long productId) {
        if (!productDao.isProductExistsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product with id [%s] not found".formatted(productId)
            );
        }
        productDao.deleteProduct(productId);
    }

    public Product updateProduct(Long productId, Product product) {

        Product products = productDao.selectProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id [%s] not found".formatted(productId)
                ));

        boolean changes = false;

        // Starting update process
        logger.debug("Updating product with ID: {}", productId);

        if (product.getProductName() != null && !product.getProductName().equals(products.getProductName())) {
            if (productDao.existsProductByName(product.getProductName())) {
                throw new DuplicateResourceException(
                        "Product already exists");
            }
            products.setProductName(product.getProductName());
            changes = true;

            // Update categoryName
            logger.debug("Update product categoryName to: {}", product.getProductName());
        }

        if (product.getDescription() != null && !product.getDescription().equals(products.getDescription())) {
            products.setDescription(product.getDescription());
            changes = true;

            // Update desc
            logger.debug("Update product description to: {}", product.getDescription());
        }

        if (product.getPrice() != null && !product.getPrice().equals(products.getPrice())) {
            products.setPrice(product.getPrice());
            changes = true;

            // Update price
            logger.debug("Update price to: {}", product.getPrice());
        }

        if (product.getQuantity() != null && !product.getQuantity().equals(products.getQuantity())) {
            products.setQuantity(product.getQuantity());
            changes = true;

            // Update quantity
            logger.debug("Update quantity to: {}", product.getQuantity());
        }

        if (product.getImageUri() != null && !product.getImageUri().equals(products.getImageUri())) {
            products.setImageUri(product.getImageUri());
            changes = true;

            // Update imageUri
            logger.debug("Update imageUri to: {}", product.getImageUri());
        }

        if (product.getCategory() != null) {
            Category updatedCategory = product.getCategory();
            if (updatedCategory.getCategoryId() != null) {
                Category categoryFromDb = categoryDao.selectCategoryById(updatedCategory.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Category with Id [%s] not found".formatted(updatedCategory.getCategoryId())
                        ));
                products.setCategory(categoryFromDb);
                changes = true;

                // Update product Category
                logger.debug("Update category to: {}", categoryFromDb.getCategoryName());
            } else {
                throw new RequestValidationException("Updated category must have a valid Id.");
            }
        }

        if (product.isAvailable() != products.isAvailable()) {
            products.setAvailable(product.isAvailable());
            changes = true;

            // Update availability
            logger.debug("Update product availability to: {}");
        }

        if (product.getManufactureDate() != null && !product.getManufactureDate().equals(products.getManufactureDate())) {
            products.setManufactureDate(product.getManufactureDate());
            changes = true;
        }

        if (product.getExpiryDate() != null && !product.getExpiryDate().equals(products.getExpiryDate())) {
            products.setExpiryDate(product.getExpiryDate());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No changes were made");
        }

        // Log that the update process is completed
        logger.debug("Update process completed for product with ID: {}", productId);

        productDao.updateProduct(product);

        return product;
    }

    // TODO: Handle image upload and retrieval

    public void updateProductPrice(Long productId, String price) {
        if (!productDao.isProductExistsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product with id [%s] not found".formatted(productId)
            );
        }
        productDao.updateProductPrice(productId, price);
    }

    public void updateProductQuantity(Long productId, int
            quantity) {
        if (!productDao.isProductExistsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product with id [%s] not found".formatted(productId)
            );
        }

        // Calculate availability base on the new quantity
        boolean isAvailable = quantity > 0;

        productDao.updateProductQuantity(productId, quantity);
        productDao.updateProductAvailability(productId, isAvailable);
    }
}
