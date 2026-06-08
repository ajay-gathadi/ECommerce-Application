package com.gathadi.ajay.ecommerce.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String productDescription;
    private Long categoryId;
    private String categoryName;
    private String productImage;
    private Integer productQuantity;
    private Double productPrice;
    private Double productDiscount;
    private Double productSpecialPrice;
}
