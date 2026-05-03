package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.payload.CategoryDTO;
import com.gathadi.ajay.ecommerce.payload.CategoryResponse;

public interface CategoryService {
    public CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
