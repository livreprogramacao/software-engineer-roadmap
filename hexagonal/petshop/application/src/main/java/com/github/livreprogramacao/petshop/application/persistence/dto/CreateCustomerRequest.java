package com.github.livreprogramacao.petshop.application.jpa.dto;

import com.petshop.company.domain.model.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de cliente")
public class CreateCustomerRequest {

    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João Silva")
    private String name;

    @NotBlank
    @Schema(description = "Telefone de contato", example = "21999998888")
    private String phone;

    @CPF
    @NotBlank
    @Schema(description = "CPF do cliente (11 dígitos)", example = "52998224725")
    private String cpf;

    @NotNull
    @Schema(description = "Data de nascimento", example = "1990-05-15")
    private LocalDate bornDate;

    @Valid
    @NotNull
    @Schema(description = "Endereço do cliente")
    private CreateAddressRequest address;
}
