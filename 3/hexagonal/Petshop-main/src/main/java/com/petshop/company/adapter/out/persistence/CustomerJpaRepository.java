package com.petshop.company.adapter.out.persistence;

import com.petshop.company.domain.model.Customer;
import com.petshop.company.domain.port.out.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long>, CustomerRepository {

    @Override
    @EntityGraph(attributePaths = {"address"})
    List<Customer> findAllWithAddress();
}
