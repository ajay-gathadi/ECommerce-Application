package com.gathadi.ajay.ecommerce.controller;

import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.payload.CategoryDTO;
import com.gathadi.ajay.ecommerce.payload.CategoryResponse;
import com.gathadi.ajay.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId) {

        Category updatedCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>("Category with categoryId: " + updatedCategory.getCategoryId() +
                " updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}
