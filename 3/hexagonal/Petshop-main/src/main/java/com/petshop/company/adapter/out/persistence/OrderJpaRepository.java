package com.petshop.company.adapter.out.persistence;

import com.petshop.company.domain.model.Order;
import com.petshop.company.domain.port.out.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderRepository {
}
