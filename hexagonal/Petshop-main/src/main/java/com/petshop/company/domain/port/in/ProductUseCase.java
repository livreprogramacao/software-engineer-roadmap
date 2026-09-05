package com.petshop.company.domain.port.in;

import com.petshop.company.domain.dto.CreateProductRequest;
import com.petshop.company.domain.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductUseCase {

    ProductDTO createProduct(CreateProductRequest request);
    Page<ProductDTO> getAllProducts(int page, int size);
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id, CreateProductRequest request);
    void deleteProduct(Long id);
}
