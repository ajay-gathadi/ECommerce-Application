package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryServiceImplementation implements CategoryService{

    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        categories.add(category);
    }
}
