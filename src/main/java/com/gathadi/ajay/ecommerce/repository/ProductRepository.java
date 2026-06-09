package com.gathadi.ajay.ecommerce.repository;

import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageable);

    boolean existsByProductNameIgnoreCase(String productName);
}

