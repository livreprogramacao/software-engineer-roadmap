package com.github.livreprogramacao.petshop.application.jpa.dto;

import com.petshop.company.domain.model.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de produto")
public class CreateProductRequest {

    @NotBlank
    @Schema(description = "Nome do produto", example = "Ração Premium Cães")
    private String name;

    @Schema(description = "Descrição do produto", example = "Ração premium para cães adultos de porte médio")
    private String description;

    @NotNull
    @Schema(description = "Categoria do produto")
    private Category category;

    @NotNull
    @Positive
    @Schema(description = "Preço do produto", example = "89.90", minimum = "0.01")
    private BigDecimal price;
}
