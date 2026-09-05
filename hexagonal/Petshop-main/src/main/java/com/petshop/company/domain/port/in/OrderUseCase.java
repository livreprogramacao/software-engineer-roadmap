package com.petshop.company.domain.port.in;

import com.petshop.company.domain.dto.CreateOrderRequest;
import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.model.enums.OrderStatus;

import java.util.List;

public interface OrderUseCase {
    OrderDTO createOrder(CreateOrderRequest request);
    OrderDTO getOrderById(Long id);
    List<OrderDTO> getAllOrders();
    OrderDTO updateOrderStatus(Long id,OrderStatus status);
}
