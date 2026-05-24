package com.gathadi.ajay.ecommerce.controller;

import com.gathadi.ajay.ecommerce.model.Product;
import com.gathadi.ajay.ecommerce.payload.ProductDTO;
import com.gathadi.ajay.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService  productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId){

        ProductDTO productToBeSaved = productService.addProduct(product, categoryId);

        return new ResponseEntity<>(productToBeSaved, HttpStatus.CREATED);
    }

}
