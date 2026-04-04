package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImplementation implements CategoryService {

    private final List<Category> categories = new ArrayList<>();
    public long nextId = 1;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return "Category with categoryId: " + categoryId + " deleted successfully.";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Optional<Category> optionalCategory = categories.stream()
                .filter(currentCategory -> currentCategory.getCategoryId().equals(categoryId))
                .findFirst();

        if (optionalCategory.isPresent()) {
            Category currentCategory = optionalCategory.get();
            currentCategory.setCategoryName(category.getCategoryName());
            return currentCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}
