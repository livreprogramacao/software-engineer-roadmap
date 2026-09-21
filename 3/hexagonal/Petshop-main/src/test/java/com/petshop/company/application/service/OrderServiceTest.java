package com.petshop.company.application.service;

import com.petshop.company.adapter.out.messaging.OrderJmsProducer;
import com.petshop.company.domain.dto.CreateOrderRequest;
import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.exception.OrderNotFoundException;
import com.petshop.company.domain.model.Order;
import com.petshop.company.domain.model.Product;
import com.petshop.company.domain.model.enums.OrderStatus;
import com.petshop.company.domain.port.out.OrderRepository;
import com.petshop.company.domain.port.out.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderJmsProducer jmsProducer;

    @InjectMocks
    private OrderService orderService;

    private CreateOrderRequest createSampleRequest() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("João Silva");
        request.setProductId(1L);
        request.setQuantity(2);
        return request;
    }

    private Order createSampleOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerName("João Silva");
        order.setQuantity(2);
        order.setStatus(OrderStatus.PENDING);

        Product product = new Product();
        product.setId(1L);
        product.setName("Ração Premium");
        order.setProduct(product);

        return order;
    }

    private Product createSampleProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Ração Premium");
        return product;
    }

    @Test
    void createOrder_shouldSaveAndSendToQueue() {
        CreateOrderRequest request = createSampleRequest();

        when(productRepository.findById(1L)).thenReturn(Optional.of(createSampleProduct()));
        when(orderRepository.save(any(Order.class))).thenReturn(createSampleOrder());

        OrderDTO result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals("João Silva", result.getCustomerName());
        assertEquals(OrderStatus.PENDING, result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(jmsProducer, times(1)).sendOrder(any(OrderDTO.class));
    }

    @Test
    void getOrderById_whenExists_shouldReturnDTO() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(createSampleOrder()));

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals("João Silva", result.getCustomerName());
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_whenNotExists_shouldThrowException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(99L));
        verify(orderRepository, times(1)).findById(99L);
    }
}
