package com.github.livreprogramacao.petshop.domain.dto;

import com.github.livreprogramacao.petshop.domain.model.enums.Category;
////import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
////@Schema(description = "Representação de um produto")
public class ProductDTO {

    ////@Schema(description = "ID do produto", example = "1")
    private Long id;

    ////@Schema(description = "Nome do produto", example = "Ração Premium Cães")
    private String name;

    ////@Schema(description = "Descrição do produto", example = "Ração premium para cães adultos")
    private String description;

    ////@Schema(description = "Categoria do produto", example = "FOOD")
    private Category category;

    ////@Schema(description = "Preço do produto", example = "89.90")
    private BigDecimal price;
}
