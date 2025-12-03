package com.example.myFinance.service;



import com.example.myFinance.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Long userId, Category category);

    Category updateCategory(Long categoryId, Category updatedCategory);

    void deleteCategory(Long categoryId);

    Category getCategoryById(Long categoryId);

    List<Category> getCategoriesForUser(Long userId);
}

