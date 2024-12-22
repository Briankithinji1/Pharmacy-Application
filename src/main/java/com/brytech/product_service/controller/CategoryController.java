package com.brytech.product_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.brytech.product_service.dto.CategoryDto;
import com.brytech.product_service.exception.ApiError;
import com.brytech.product_service.service.CategoryService;

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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category", description = "Adds a new category to the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Category created successfully",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieves a category by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "404", description = "Category not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get category by name", description = "Retrieves a category by its name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "404", description = "Category not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CategoryDto> findCategoryByName(@PathVariable String name) {
        CategoryDto category = categoryService.findCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Get categories by parent category", description = "Retrieves categories under a specific parent category")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
        @ApiResponse(responseCode = "404", description = "Parent category not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<CategoryDto>> findCategoriesByParentCategory(@PathVariable Long parentId) {
        List<CategoryDto> categories = categoryService.findCategoriesByParentCategory(parentId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    @Operation(summary = "Search categories by name", description = "Searches for categories containing a specific name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<CategoryDto>> findCategoriesByNameContaining(@RequestParam String name) {
        List<CategoryDto> categories = categoryService.findCategoryByNameContaining(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves a paginated list of all categories")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class))))
    })
    public ResponseEntity<Page<CategoryDto>> findAllCategories(Pageable pageable) {
        Page<CategoryDto> categories = categoryService.findAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Updates an existing category by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category updated successfully",
                content = @Content(schema = @Schema(implementation = CategoryDto.class))),
        @ApiResponse(responseCode = "404", description = "Category not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Deletes a category by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/findByIds")
    @Operation(summary = "Get categories by IDs", description = "Retrieves categories by a list of IDs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
        @ApiResponse(responseCode = "404", description = "Categories not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<CategoryDto>> findAllCategoriesByIds(@RequestBody List<Long> ids) {
        List<CategoryDto> categories = categoryService.findAllCategoriesByIds(ids);
        return ResponseEntity.ok(categories);
    }
}
