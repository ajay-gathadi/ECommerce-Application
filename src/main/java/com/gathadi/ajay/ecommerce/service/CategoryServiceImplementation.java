package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryServiceImplementation implements CategoryService{

    private List<Category> categories = new ArrayList<>();
    public long nextId = 1 ;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        boolean removed = categories.removeIf(category -> category.getCategoryId() == categoryId);
        if (!removed) {
            return "Category not found!!";
        }
        return "Category with categoryId: " + categoryId + "deleted successfully.";
    }
}
