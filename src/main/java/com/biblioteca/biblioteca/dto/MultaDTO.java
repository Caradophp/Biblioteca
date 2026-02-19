package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MultaDTO(
        @NotNull(message = "Id do emprétimo deve ser informado")
        long idEmprestimo,

        @NotNull(message = "Valor da multa deve ser informado")
        float multaValor,

        @NotBlank(message = "Forma de pagamento deve ser irformada")
        String tipoPagamento
) {
}
