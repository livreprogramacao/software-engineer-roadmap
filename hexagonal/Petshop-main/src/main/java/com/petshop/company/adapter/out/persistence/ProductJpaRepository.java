package com.petshop.company.adapter.out.persistence;

import com.petshop.company.domain.model.Product;
import com.petshop.company.domain.port.out.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {
}
