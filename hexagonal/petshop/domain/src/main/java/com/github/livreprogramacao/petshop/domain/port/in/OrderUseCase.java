package com.github.livreprogramacao.petshop.domain.port.in;

//import com.github.livreprogramacao.petshop.domain.dto.CreateOrderRequest;
import com.github.livreprogramacao.petshop.domain.dto.OrderDTO;
//import com.github.livreprogramacao.petshop.domain.model.enums.OrderStatus;

import java.util.List;

public interface OrderUseCase {
    //OrderDTO createOrder(CreateOrderRequest request);
    OrderDTO getOrderById(Long id);
    List<OrderDTO> getAllOrders();
//    OrderDTO updateOrderStatus(Long id,OrderStatus status);
}
