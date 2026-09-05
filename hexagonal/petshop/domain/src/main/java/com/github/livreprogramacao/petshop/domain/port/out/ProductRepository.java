package com.github.livreprogramacao.petshop.domain.port.out;


//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import com.github.livreprogramacao.petshop.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
//    Page<Product> findAll(Pageable pageable);
    void delete(Product product);
}
