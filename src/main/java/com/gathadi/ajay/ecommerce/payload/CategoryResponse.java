package com.gathadi.ajay.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> categories;
    Integer pageNumber;
    Integer pageSize;
    Long totalElements;
    Integer totalPages;
    boolean lastPage;
}

