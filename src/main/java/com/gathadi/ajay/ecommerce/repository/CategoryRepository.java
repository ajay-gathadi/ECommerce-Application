package com.gathadi.ajay.ecommerce.repository;

import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
    Spring sees this CategoryRepository repository interface extending JpaRepository,
    creates a proxy implementation at runtime,
    and that proxy executes all the database operations on my behalf —
    so I never have to write the implementation manually.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
