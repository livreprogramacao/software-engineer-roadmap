package com.petshop.company.application.service;

import com.petshop.company.domain.dto.CreateProductRequest;
import com.petshop.company.domain.dto.ProductDTO;
import com.petshop.company.domain.exception.ProductNotFoundException;
import com.petshop.company.domain.model.Product;
import com.petshop.company.domain.port.in.ProductUseCase;
import com.petshop.company.domain.port.out.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements ProductUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        Product saved = productRepository.save(product);
        log.info("Product created with id: {}", saved.getId());
        return toDTO(saved);
    }

    @Override
    public Page<ProductDTO> getAllProducts(int page, int size) {
        log.info("Fetching all products");

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        Page<ProductDTO> result = productPage.map(this::toDTO);
        log.info("Found {} products", result.getTotalElements());

        return result;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new ProductNotFoundException(id);
                });
        return toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, CreateProductRequest request) {
        log.info("Updating product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        Product saved = productRepository.save(product);
        log.info("Product {} updated successfully", id);
        return toDTO(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
        log.info("Product {} deleted successfully", id);
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
