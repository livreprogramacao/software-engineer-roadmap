package com.github.livreprogramacao.petshop.domain.dto;

import com.github.livreprogramacao.petshop.domain.model.Address;
////import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
////@Schema(description = "Representação de um cliente")
public class CustomerDTO {

    ////@Schema(description = "ID do cliente", example = "1")
    private Long id;

    ////@Schema(description = "Endereço do cliente")
    private Address address;

    ////@Schema(description = "Nome completo do cliente", example = "João Silva")
    private String name;

    ////@Schema(description = "CPF do cliente", example = "52998224725")
    private String cpf;

    ////@Schema(description = "Data de nascimento", example = "1990-05-15")
    private LocalDate bornDate;

    ////@Schema(description = "Data de criação do registro", example = "2026-01-15")
    private LocalDate createdAt;

    ////@Schema(description = "Telefone de contato", example = "21999998888")
    private String phone;
}
