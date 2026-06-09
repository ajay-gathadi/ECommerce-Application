package com.gathadi.ajay.ecommerce.service;

import com.gathadi.ajay.ecommerce.exceptions.APIException;
import com.gathadi.ajay.ecommerce.exceptions.ResourceNotFoundException;
import com.gathadi.ajay.ecommerce.model.Category;
import com.gathadi.ajay.ecommerce.model.Product;
import com.gathadi.ajay.ecommerce.payload.ProductDTO;
import com.gathadi.ajay.ecommerce.payload.ProductResponse;
import com.gathadi.ajay.ecommerce.repository.CategoryRepository;
import com.gathadi.ajay.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.images.directory}")
    private String path;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        if(productRepository.existsByProductNameIgnoreCase(productDTO.getProductName())){
            throw new APIException("Product with the same name already exists", HttpStatus.CONFLICT);
        }

        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setCategory(category);
        product.setProductSpecialPrice(product.getProductPrice() - ((product.getProductDiscount() * 0.01) * product.getProductPrice()));
        Product savedProduct = productRepository.save(product);

        return mapToDTO(savedProduct);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Product> pageDetails = productRepository.findAll(pageable);

        List<Product> productList = pageDetails.getContent();

//        List<Product> productList = paginationAndSorting(pageNumber, pageSize, sortBy, sortOrder);

        if(productList.isEmpty()){
            throw new APIException("No products found", HttpStatus.NOT_FOUND);
        }

        List<ProductDTO> productDTOS = productList.stream()
                .map(eachProduct -> modelMapper.map(eachProduct, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);
        productResponse.setPageNumber(pageDetails.getNumber());
        productResponse.setPageSize(pageDetails.getSize());
        productResponse.setTotalProducts(pageDetails.getNumberOfElements());
        productResponse.setTotalPages(pageDetails.getTotalPages());
        productResponse.setLastPage(pageDetails.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

//        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        Page<Product> productDetails = productRepository.findProductsByCategory(category, pageable);
//
//        List<Product> productList = productDetails.getContent();

//        List<ProductDTO> productDTOS = productList.stream()
//                .map(eachProduct -> modelMapper.map(eachProduct, ProductDTO.class))
//                .toList();

        ProductResponse productResponse = new ProductResponse();
//        productResponse.setProducts(productDTOS);
//        productResponse.setPageNumber(productDetails.getNumber());
//        productResponse.setPageSize(productDetails.getSize());
//        productResponse.setTotalProducts(productDetails.getNumberOfElements());
//        productResponse.setTotalPages(productDetails.getTotalPages());
//        productResponse.setLastPage(productDetails.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
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
        Product productToBeUpdated = findProductById(productId);

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
        Product productToBePatched = findProductById(productId);

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
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = findProductById(productId);
        String fileName = fileService.uploadImage(path, image);
        product.setProductImage(fileName);
        productRepository.save(product);
        return mapToDTO(product);
    }


    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = findProductById(productId);
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    private List<Product> paginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder)
    {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Product> pageDetails = productRepository.findAll(pageable);

        return pageDetails.getContent();
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    }

    private ProductDTO mapToDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }
}
