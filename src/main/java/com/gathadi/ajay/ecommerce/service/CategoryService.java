package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.payload.CategoryDTO;
import com.gathadi.ajay.ecommerce.payload.CategoryResponse;
import org.springframework.http.HttpStatus;

public interface CategoryService {
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
