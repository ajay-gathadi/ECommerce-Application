package com.gathadi.ajay.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    private String productName;
    private String productImage;
    private String productDescription;
    private Integer productQuantity;
    private Double productPrice;
    private Double productDiscount;
    private Double productSpecialPrice;

    // Architectural Constraint: Force LAZY fetching to prevent N+1 query paralysis.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Architectural Constraint: Force LAZY fetching.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        // Accessing this.getProductId() via getter is required to force initialization 
        // if 'this' happens to be an uninitialized Hibernate proxy.
        return getProductId() != null && getProductId().equals(product.getProductId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
