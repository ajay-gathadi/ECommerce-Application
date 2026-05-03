package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.exceptions.APIException;
import com.gathadi.ajay.ecommerce.exceptions.ResourceNotFoundException;
import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.payload.CategoryDTO;
import com.gathadi.ajay.ecommerce.payload.CategoryResponse;
import com.gathadi.ajay.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()){
            throw new APIException("There are no categories present", HttpStatus.NOT_FOUND);
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(currentCategory -> modelMapper.map(currentCategory, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);

        Category newCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if(newCategory != null){
            throw new APIException("Category with categoryName: " + categoryDTO.getCategoryName() + " already exists.",  HttpStatus.CONFLICT);
        }

        Category categoryToBeSaved = categoryRepository.save(category);

        return modelMapper.map(categoryToBeSaved, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category categoryToBeDeleted = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.deleteById(categoryId);
        return modelMapper.map(categoryToBeDeleted, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category currentCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        currentCategory.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(currentCategory);
        return modelMapper.map(currentCategory, CategoryDTO.class);
    }
}
