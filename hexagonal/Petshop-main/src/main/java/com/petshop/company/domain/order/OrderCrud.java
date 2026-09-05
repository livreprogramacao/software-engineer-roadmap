package com.petshop.company.domain.order;

import com.petshop.company.adapter.out.messaging.OrderJmsProducer;
import com.petshop.company.adapter.out.persistence.mapper.OrderMapper;
import com.petshop.company.application.service.OrderService;
import com.petshop.company.domain.dto.CreateOrderRequest;
import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.exception.BadRequestException;
import com.petshop.company.domain.exception.InvalidOrderStatusException;
import com.petshop.company.domain.exception.OrderNotFoundException;
import com.petshop.company.domain.exception.ProductNotFoundException;
import com.petshop.company.domain.model.Order;
import com.petshop.company.domain.model.Product;
import com.petshop.company.domain.model.enums.OrderStatus;
import com.petshop.company.domain.port.out.OrderRepository;
import com.petshop.company.domain.port.out.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderCrud {

    private static final Logger log = LoggerFactory.getLogger(OrderCrud.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderJmsProducer jmsProducer;

    public OrderCrud(OrderRepository orderRepository, ProductRepository productRepository, OrderJmsProducer jmsProducer) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.jmsProducer = jmsProducer;
    }

    public OrderDTO createOrder(OrderDTO dto) {

        if (dto.getProductId() == null) {
            throw new BadRequestException("productId is required");
        }

        log.info("Creating order for customer: {}", dto.getCustomerName());
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", dto.getProductId());
                    return new ProductNotFoundException(dto.getProductId());
                });

        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setProduct(product);
        order.setQuantity(dto.getQuantity());
        order.setStatus(OrderStatus.PENDING);

        Order saved = orderRepository.save(order);
        OrderDTO orderDTO = OrderMapper.toDTO(saved);
        log.info("Order created with id: {}, sending to queue", saved.getId());

        jmsProducer.sendOrder(orderDTO);

        return orderDTO;
    }

    public OrderDTO getOrderById(Long orderId) {
        log.info("Fetching order with orderId: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with orderId: {}", orderId);
                    return new OrderNotFoundException(orderId);
                });
        return OrderMapper.toDTO(order);
    }

    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderDTO> orders = orderRepository.findAll().stream()
                .map(OrderMapper::toDTO)
                .toList();
        log.info("Found {} orders", orders.size());
        return orders;
    }

    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {

        log.info("Updating order {} status to {}", orderId, status);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            return new OrderNotFoundException(orderId);
        });

        if (!order.getStatus().canTransactionTo(status)) {
            throw new InvalidOrderStatusException(
                    "Cannot transition from " + order.getStatus() + " to " + status);
        }

        order.setStatus(status);
        Order saved = orderRepository.save(order);
        log.info("Order {} status updated to {}", orderId, status);
        return OrderMapper.toDTO(saved);
    }

}