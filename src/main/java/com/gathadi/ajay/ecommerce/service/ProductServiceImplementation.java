package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.exceptions.ResourceNotFoundException;
import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.model.Product;
import com.gathadi.ajay.ecommerce.payload.ProductDTO;
import com.gathadi.ajay.ecommerce.repository.CategoryRepository;
import com.gathadi.ajay.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setCategory(category);
        product.setProductSpecialPrice(product.getProductPrice() - ((product.getDiscount() * 0.01) * product.getProductPrice()));
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
