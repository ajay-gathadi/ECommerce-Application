package com.gathadi.ajay.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
