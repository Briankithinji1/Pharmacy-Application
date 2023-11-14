package com.example.Pharmacy.Application.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("allProducts")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("byId/{productId}")
    public ProductDTO getProductById(
            @PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("byCategory/{categoryId}")
    public List<ProductDTO> getProductByCategory(
            @PathVariable("categoryId") Long categoryId) {
        return productService.getProductByCategory(categoryId);
    }

    @PostMapping("addProduct")
    public ResponseEntity<?> addProduct(
            @RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping("delete/{productId}")
    public void deleteProduct(
            @PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping("update/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("quantity/{productId}")
    public void updateProductQuantity(
            @PathVariable("productId") Long productId,
            @RequestBody int quantity) {
        productService.updateProductQuantity(productId, quantity);
    }

    @PatchMapping("price/{productId}")
    public void updateProductPrice(
            @PathVariable("productId") Long productId,
            @RequestBody String price) {
        productService.updateProductPrice(productId, price);
    }
}
