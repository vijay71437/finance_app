package com.example.myFinance.service.Impl;


import com.example.myFinance.entity.Category;
import com.example.myFinance.entity.User;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.CategoryRepository;
import com.example.myFinance.repository.UserRepository;
import com.example.myFinance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public Category createCategory(Long userId, Category category) {
        User user = getUserEntity(userId);
        category.setId(null);
        category.setUser(user);

        // Optional: prevent duplicate category names for a user
        if (categoryRepository.existsByUserAndNameIgnoreCase(user, category.getName())) {
            throw new IllegalArgumentException(
                    "Category with name '" + category.getName() + "' already exists for this user");
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category existing = getCategoryEntity(categoryId);

        existing.setName(updatedCategory.getName());
        existing.setType(updatedCategory.getType());
        existing.setColorHex(updatedCategory.getColorHex());

        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category existing = getCategoryEntity(categoryId);
        categoryRepository.delete(existing);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return getCategoryEntity(categoryId);
    }

    @Override
    public List<Category> getCategoriesForUser(Long userId) {
        User user = getUserEntity(userId);
        return categoryRepository.findByUser(user);
    }

    // ---------- Helpers ----------

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }
}

