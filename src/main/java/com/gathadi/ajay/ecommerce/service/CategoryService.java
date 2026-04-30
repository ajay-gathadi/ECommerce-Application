package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.payload.CategoryDTO;
import com.gathadi.ajay.ecommerce.payload.CategoryResponse;

public interface CategoryService {
    public CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}
