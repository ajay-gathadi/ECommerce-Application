package com.gathadi.ajay.ecommerce.repository;

import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByProductPriceAsc(Category category);
}

