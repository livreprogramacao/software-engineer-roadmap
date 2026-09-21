package com.petshop.company.consumer;

import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.model.enums.OrderStatus;
import com.petshop.company.domain.port.out.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    private final OrderRepository orderRepository;

    public OrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @JmsListener(destination = "order-queue")
    public void processOrder(OrderDTO orderDTO) {
        log.info("Received order from queue: {}", orderDTO.getId());

        orderRepository.findById(orderDTO.getId()).ifPresent(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            log.info("Order {} processed successfully", order.getId());
        });
    }
}
