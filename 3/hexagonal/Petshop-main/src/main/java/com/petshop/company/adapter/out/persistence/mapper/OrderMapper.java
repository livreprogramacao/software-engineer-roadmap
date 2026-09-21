package com.petshop.company.adapter.out.persistence.mapper;

import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.model.Order;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setProduct(order.getProduct());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        return dto;
    }

    public static Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setCustomerName(dto.getCustomerName());
        order.setProduct(dto.getProduct());
        order.setQuantity(dto.getQuantity());
        order.setStatus(dto.getStatus());
        return order;
    }
}
