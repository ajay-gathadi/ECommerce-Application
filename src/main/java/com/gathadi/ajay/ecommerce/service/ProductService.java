package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.payload.ProductDTO;
import com.gathadi.ajay.ecommerce.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long category);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);

    ProductResponse searchProductsByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO patchProduct(ProductDTO productDTO, Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
