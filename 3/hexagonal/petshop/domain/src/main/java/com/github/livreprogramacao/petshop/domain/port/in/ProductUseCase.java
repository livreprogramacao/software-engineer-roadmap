package com.github.livreprogramacao.petshop.domain.port.in;

//import com.github.livreprogramacao.petshop.domain.dto.CreateProductRequest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import com.github.livreprogramacao.petshop.domain.dto.ProductDTO;

public interface ProductUseCase {

//    ProductDTO createProduct(CreateProductRequest request);
//    Page<ProductDTO> getAllProducts(int page, int size);
    ProductDTO getProductById(Long id);
//    ProductDTO updateProduct(Long id, CreateProductRequest request);
    void deleteProduct(Long id);
}
