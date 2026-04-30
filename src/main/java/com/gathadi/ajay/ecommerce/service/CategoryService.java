package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}
