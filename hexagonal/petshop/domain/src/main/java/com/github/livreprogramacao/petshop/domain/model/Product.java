package com.github.livreprogramacao.petshop.domain.model;

import com.github.livreprogramacao.petshop.domain.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long Id;

    private String name;

    private String description;

    private Category category;

    private BigDecimal price;

}
