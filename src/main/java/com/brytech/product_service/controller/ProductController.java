package com.brytech.product_service.controller;

import jakarta.validation.Valid;

import com.brytech.product_service.dto.ProductDto;
import com.brytech.product_service.exception.ApiError;
import com.brytech.product_service.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Adds a new product to the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        ProductDto product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get product by name", description = "Retrieves a product by its name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ProductDto> findProductByName(@PathVariable String name) {
        ProductDto product = productService.findProductByName(name);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))))
    })
    public ResponseEntity<Page<ProductDto>> findAllProducts(Pageable pageable) {
        Page<ProductDto> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Searches for products containing a specific name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<ProductDto>> findProductsByNameContaining(@RequestParam String name, Pageable pageable) {
        Page<ProductDto> products = productService.findProductByNameContaining(name, pageable);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
