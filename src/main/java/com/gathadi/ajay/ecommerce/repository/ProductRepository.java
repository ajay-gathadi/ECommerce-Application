package com.gathadi.ajay.ecommerce.repository;

import com.gathadi.ajay.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
