package com.example.Pharmacy.Application.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("byId/{categoryId}")
    public CategoryDTO getCategoryById(
            @PathVariable("categoryId") Long categoryId
    ) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("byCategoryName/{categoryName}")
    public CategoryDTO getCategoryByName(
            @PathVariable("categoryName") String categoryName
    ) {
        return categoryService.getCategoryByName(categoryName);
    }

    @GetMapping("allCategories")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("byProductId/{productId}")
    public List<CategoryDTO> getCategoryByProductId(
            @PathVariable("productId") Long productId
    ) {
       return categoryService.getCategoryByProductId(productId);
    }

    @PostMapping("addCategory")
    public ResponseEntity<?> addCategory(
            @RequestBody Category category
    ) {
        categoryService.addCategory(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{categoryId}")
    public void deleteCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);
    }

    @PutMapping("update/{categoryId}")
    public void updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody Category category
    ) {
        categoryService.updateCategory(categoryId, category);
    }

    // TODO: Handle uploading of the image

    // TODO: Handle getting the image
}
