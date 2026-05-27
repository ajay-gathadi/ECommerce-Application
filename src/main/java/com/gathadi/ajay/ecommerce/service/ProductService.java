package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.model.Product;
import com.gathadi.ajay.ecommerce.payload.ProductDTO;
import com.gathadi.ajay.ecommerce.payload.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDTO addProduct(Product product, Long category);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);
}
