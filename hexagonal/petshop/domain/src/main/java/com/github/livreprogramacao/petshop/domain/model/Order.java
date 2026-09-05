package com.github.livreprogramacao.petshop.domain.model;

import com.github.livreprogramacao.petshop.domain.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private String customerName;

    private Integer quantity;

    private Product product;

    private OrderStatus status;

}
