package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.exceptions.ResourceNotFoundException;
import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.model.Product;
import com.gathadi.ajay.ecommerce.payload.ProductDTO;
import com.gathadi.ajay.ecommerce.payload.ProductResponse;
import com.gathadi.ajay.ecommerce.repository.CategoryRepository;
import com.gathadi.ajay.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setCategory(category);
        product.setProductSpecialPrice(product.getProductPrice() - ((product.getProductDiscount() * 0.01) * product.getProductPrice()));
        Product savedProduct = productRepository.save(product);

        return mapToDTO(savedProduct);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductDTO> productDTOS = productList.stream()
                .map(eachProduct -> modelMapper.map(eachProduct, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> productList = productRepository.findByCategoryOrderByProductPriceAsc(category);
        List<ProductDTO> productDTOS = productList.stream()
                .map(eachProduct -> modelMapper.map(eachProduct, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword) {
        List<Product> productList = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");

        List<ProductDTO> productDTOS = productList.stream()
                .map(currentProduct -> modelMapper.map(currentProduct, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product productToBeUpdated = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productToBeUpdated.setProductName(product.getProductName());
        productToBeUpdated.setProductDescription(product.getProductDescription());
        productToBeUpdated.setProductPrice(product.getProductPrice());
        productToBeUpdated.setProductQuantity(product.getProductQuantity());
        productToBeUpdated.setProductDiscount(product.getProductDiscount());
        productToBeUpdated.setProductSpecialPrice(product.getProductPrice() - ((product.getProductDiscount() * 0.01) * product.getProductPrice()));

        Product savedProduct = productRepository.save(productToBeUpdated);
        return mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO patchProduct(ProductDTO productDTO, Long productId) {
        Product productToBePatched = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if(productDTO.getProductName() != null){
            productToBePatched.setProductName(productDTO.getProductName());
        }

        if (productDTO.getProductImage() != null) {
            productToBePatched.setProductImage(productDTO.getProductImage());
        }

        if(productDTO.getProductDescription() != null){
            productToBePatched.setProductDescription(productDTO.getProductDescription());
        }

        if(productDTO.getProductQuantity() != null){
            productToBePatched.setProductQuantity(productDTO.getProductQuantity());
        }

        if (productDTO.getProductPrice() != null) {
            productToBePatched.setProductPrice(productDTO.getProductPrice());
        }

        if(productDTO.getProductDiscount() != null){
            double price = productDTO.getProductPrice() != null ?
                    productDTO.getProductPrice() : productToBePatched.getProductPrice();

            productToBePatched.setProductDiscount(productDTO.getProductDiscount());
            productToBePatched.setProductSpecialPrice(
                    price - (productDTO.getProductDiscount() * 0.01)  * price
            );
        }

        if(productDTO.getCategoryId() != null){
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",  productDTO.getCategoryId()));

            productToBePatched.setCategory(category);
        }

        Product savedProduct = productRepository.save(productToBePatched);
        return mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    private ProductDTO mapToDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }
}
