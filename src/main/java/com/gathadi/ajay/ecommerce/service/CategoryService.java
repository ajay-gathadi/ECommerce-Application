package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    void createCategory(Category category);
}
