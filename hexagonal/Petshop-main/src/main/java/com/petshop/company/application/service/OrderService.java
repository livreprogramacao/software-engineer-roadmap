package com.petshop.company.application.service;

import com.petshop.company.adapter.out.messaging.OrderJmsProducer;
import com.petshop.company.adapter.out.persistence.mapper.OrderMapper;
import com.petshop.company.domain.dto.CreateOrderRequest;
import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.exception.BadRequestException;
import com.petshop.company.domain.exception.InvalidOrderStatusException;
import com.petshop.company.domain.exception.OrderNotFoundException;
import com.petshop.company.domain.exception.ProductNotFoundException;
import com.petshop.company.domain.model.Order;
import com.petshop.company.domain.model.Product;
import com.petshop.company.domain.model.enums.OrderStatus;
import com.petshop.company.domain.port.in.OrderUseCase;
import com.petshop.company.domain.port.out.OrderRepository;
import com.petshop.company.domain.port.out.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderUseCase {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderJmsProducer jmsProducer;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderJmsProducer jmsProducer) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.jmsProducer = jmsProducer;
    }

    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {

        if (request.getProductId() == null) {
            throw new BadRequestException("productId is required");
        }

        log.info("Creating order for customer: {}", request.getCustomerName());
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", request.getProductId());
                    return new ProductNotFoundException(request.getProductId());
                });

        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.PENDING);

        Order saved = orderRepository.save(order);
        OrderDTO orderDTO = OrderMapper.toDTO(saved);
        log.info("Order created with id: {}, sending to queue", saved.getId());

        jmsProducer.sendOrder(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", id);
                    return new OrderNotFoundException(id);
                });
        return OrderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderDTO> orders = orderRepository.findAll().stream()
                .map(OrderMapper::toDTO)
                .toList();
        log.info("Found {} orders", orders.size());
        return orders;
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, OrderStatus status) {

        log.info("Updating order {} status to {}", id, status);
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            return new OrderNotFoundException(id);
        });

        if (!order.getStatus().canTransactionTo(status)) {
            throw new InvalidOrderStatusException(
                    "Cannot transition from " + order.getStatus() + " to " + status);
        }

        order.setStatus(status);
        Order saved = orderRepository.save(order);
        log.info("Order {} status updated to {}", id, status);
        return OrderMapper.toDTO(saved);
    }
}
