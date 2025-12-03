package com.example.myFinance.controller;

import com.example.myFinance.entity.Category;
import com.example.myFinance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    // Create category for a user
    @PostMapping("/users/{userId}/categories")
    public ResponseEntity<Category> createCategory(@PathVariable Long userId,
                                                   @RequestBody Category category) {
        Category created = categoryService.createCategory(userId, category);
        return ResponseEntity.ok(created);
    }

    // Get all categories for a user
    @GetMapping("/users/{userId}/categories")
    public ResponseEntity<List<Category>> getCategoriesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryService.getCategoriesForUser(userId));
    }

    // Get single category
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    // Update category
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                   @RequestBody Category category) {
        Category updated = categoryService.updateCategory(categoryId, category);
        return ResponseEntity.ok(updated);
    }

    // Delete category
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
