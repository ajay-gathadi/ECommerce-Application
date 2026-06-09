package com.gathadi.ajay.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductDTO> products;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalProducts;
    private Integer totalPages;
    private boolean lastPage;
}
